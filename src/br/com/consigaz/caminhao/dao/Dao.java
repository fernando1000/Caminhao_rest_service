package br.com.consigaz.caminhao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utilitarios.reflexao.Query;
import utilitarios.restservice.PreencheObjetoUsandoResultSet;

public class Dao {

	private Connection connection;

	public Dao() {
		this.connection = new FabricaDeConexao().pegaConexao();
	}

	public <E> List<E> listaTodaTabela(Class<E> classe) {

		String sql = "select * from " + classe.getSimpleName();

		return (List<E>) devolveListaBaseadoEmSQL(classe, sql);
	}
	
	public <E> List<E> listaTodaTabela(Class<E> classe, Object... parametros) {

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhere_final(parametros);

		String querySelect = select + condicaoWhere;

		return (List<E>) devolveListaBaseadoEmSQL(classe, querySelect);
	}

	private <T> List<T> devolveListaBaseadoEmSQL(Class<T> classe, String sql) {

		List<T> lista = new ArrayList<T>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				lista.add((T) PreencheObjetoUsandoResultSet.devolveObjetoPreenchido(classe, resultSet));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (List<T>) lista;
	}

	public int insereOUAtualiza(Object objeto, Object... parametros) {

		Class<?> classe = objeto.getClass();

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhere_final(parametros);

		String querySelect = select + condicaoWhere;

		int deuErro = 0;

		try {
			PreparedStatement preparedStatement1 = connection.prepareStatement(querySelect);

			ResultSet resultSet = preparedStatement1.executeQuery();

			if (resultSet.next()) {

				String update = Query.criaUpdate_final(objeto, condicaoWhere);

				connection.prepareStatement(update).execute();

				System.out.println(update);
			} else {
				String insert = Query.criaInsert_final(objeto);

				connection.prepareStatement(insert).execute();

				System.out.println(insert);
			}
			resultSet.close();
			preparedStatement1.close();
		} catch (Exception exception) {

			exception.printStackTrace();
			deuErro = 1;
		}
		return deuErro;
	}
			
	private void executaSQL(String sql) {

		try {
			connection.prepareStatement(sql).execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
