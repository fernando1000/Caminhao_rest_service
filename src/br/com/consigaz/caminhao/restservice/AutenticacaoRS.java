package br.com.consigaz.caminhao.restservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao_sharedlib.enums.Generico;
import br.com.consigaz.caminhao_sharedlib.modelo.*;

@Path("Autenticacao")
public class AutenticacaoRS {

	private Dao dao = new Dao();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Login")
	public TO autenticar(Pda pda){

		TO to_interno  = null;
		
		try {
			List<Pda> listaCom1Pda = dao.listaTodaTabela(Pda.class, 
														 Pda.COLUMN_TEXT_NR_PDA, " '"+ pda.getNr_pda() +"' ", 
														 Pda.COLUMN_INTEGER_SENHA, pda.getSenha());

			if(listaCom1Pda.isEmpty()){
				
				to_interno = new TO();
				to_interno.setNr_pda(pda.getNr_pda());
				to_interno.setAchou_nr_pda(Generico.NAO_ENCONTROU_USUARIO.getValor());
				
				System.out.print("@@ listaCom1Pda.isEmpty @@ :(");	
			}
			else{	
				List<Boleto> listaBoleto = dao.listaTodaTabela(Boleto.class);
				List<Cliente_pedido> listaClientePedido = dao.listaTodaTabela(Cliente_pedido.class);
				List<Condicao_pagamento> listaCondicaoPagamento = dao.listaTodaTabela(Condicao_pagamento.class);
				List<Embarque> listaEmbarque = dao.listaTodaTabela(Embarque.class, Embarque.COLUMN_TEXT_NR_PDA, " '"+ pda.getNr_pda() +"' ");
				List<Estabelecimento_serie> listaEstabelecimentoSerie = dao.listaTodaTabela(Estabelecimento_serie.class, Estabelecimento_serie.COLUMN_TEXT_NR_PDA, " '"+ pda.getNr_pda() +"' ");
				List<Instrucao_bancaria> listaInstrucaoBancaria = dao.listaTodaTabela(Instrucao_bancaria.class);
				List<Item_nfe> listaItemNFE = dao.listaTodaTabela(Item_nfe.class);
				List<Justificativa> listaJustificativa = dao.listaTodaTabela(Justificativa.class);
				List<Local_entrega_pedido> listaLocalEntregaPedido = dao.listaTodaTabela(Local_entrega_pedido.class);
				List<Mensagem> listaMensagem = dao.listaTodaTabela(Mensagem.class);
				List<Natureza_operacao> listaNaturezaOperacao = dao.listaTodaTabela(Natureza_operacao.class);
				List<Pedido> listaPedido = dao.listaTodaTabela(Pedido.class);
				List<Produto> listaProduto = dao.listaTodaTabela(Produto.class);
				List<Vendedor> listaVendedor = dao.listaTodaTabela(Vendedor.class);
					
				to_interno = new TO(listaCom1Pda, listaBoleto, listaClientePedido, listaCondicaoPagamento, listaEmbarque,	listaEstabelecimentoSerie, 
								    listaInstrucaoBancaria, listaItemNFE, listaJustificativa, listaLocalEntregaPedido, listaMensagem, listaNaturezaOperacao, 
								    listaPedido, listaProduto, listaVendedor);
				
				to_interno.setNr_pda(pda.getNr_pda());
				to_interno.setAchou_nr_pda(Generico.ENCONTROU_USUARIO.getValor());
				
				System.out.print("@@ listaCom1Pda NAO esta vazia @@ :D");	
			}	
		} 
		catch (Exception exception) {	
			
			to_interno = new TO();
			to_interno.setNr_pda(pda.getNr_pda());
			to_interno.setAchou_nr_pda(Generico.OCORREU_ERRO.getValor());
			
			System.out.print("@@ Excecao: @@" + exception);		
		}
		return to_interno;
	}
		
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Teste")
	public String teste(){
		
		String dataAtualFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

		List<Configuracao> listaConfiguracao = dao.listaTodaTabela(Configuracao.class);
		
		Configuracao configuracao = listaConfiguracao.get(0);
		
		if(configuracao.getVersao_app() == 0 ){

			return "Erro :( "+dataAtualFormatada;
		}else{
			return "Servidor OK "+dataAtualFormatada;
		}
	}

	
}
