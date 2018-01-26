package br.com.consigaz.caminhao.restservice;

import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao_sharedlib.modelo.Configuracao;

@Path("Configuracao")
public class ConfiguracaoRS{

	private Dao dao = new Dao();

	@GET	
	@Path("/Versao")
	@Produces(MediaType.APPLICATION_JSON)
	public Configuracao Versao(){
				
		Configuracao configuracao = new Configuracao();
	
		try {		
			
			for(Configuracao conf : dao.listaTodaTabela(Configuracao.class)){
				
				configuracao.setVersao_app(conf.getVersao_app());
				configuracao.setUrl_apk(conf.getUrl_apk());	
			}		
		} 
		catch (Exception e) {
			
			e.printStackTrace();
			
			configuracao.setVersao_app(0);
			
			return configuracao;
		}
		return configuracao;
	}

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/DownloadApk")
	public Response apk(){
		
		String urlApk = null;
		
		try {
							 
			for(Configuracao conf : dao.listaTodaTabela(Configuracao.class)){
													
					urlApk = conf.getUrl_apk();	
			}			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		File file = new File(urlApk);

		 return Response.ok(file).header("Content-Disposition", "attachment; filename=" + file.getName()).build();
	}

}
