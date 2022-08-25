package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
	
	private Connection conexao;
	private static ConexaoBD instance = null;
	
	private ConexaoBD() {};
	
	public static ConexaoBD getInstance() {
		
		if(instance == null) 
			return instance = new ConexaoBD();
			
		return instance;
	}

	public Connection getConexao() {
		abrirConexao();
		return conexao;
	}
	
	private void setConexao(Connection conexao) {
		this.conexao = conexao;
	}
	
	private void abrirConexao() {	
			try {
				Class.forName("org.postgresql.Driver");
				this.setConexao(DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/dbagenda"
						+ "?useTimezone=true&serverTimezone=UTC", "postgres", "D@b@2020"));
				
			} catch (Exception e) {
				System.out.println("Erro na classe ConexaoBD laço if "+e.getMessage());
			}			
	}
	
	public boolean fecharConexao() {
		try{
			conexao.close();
			
			return true;
		}catch(SQLException e) {
			System.out.println(e);
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getCause());
			System.out.println("Erro na classe BD "+e.getMessage());
			return false;
		}
	}
	
	/*public void testeConexao() {
		try {
			Connection conn = getConexao();
			System.out.println("Conexão estabelecida com sucesso\n" +conn);
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}*/
}
