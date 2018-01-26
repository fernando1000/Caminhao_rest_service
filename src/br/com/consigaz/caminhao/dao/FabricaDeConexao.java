package br.com.consigaz.caminhao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexao {

	public Connection pegaConexao() {
		
			try {
				Class.forName("com.mysql.jdbc.Driver");

				//return DriverManager.getConnection("jdbc:mysql://172.16.0.48:3306/posvenda", "root", "c0ns1g@z"); //c0ns1g@z//root				
				return DriverManager.getConnection("jdbc:mysql://localhost/danfe_no_pda", "root", "root");

			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}		
		return null;
	}
}
