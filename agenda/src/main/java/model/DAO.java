package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	
	ConexaoBD conexao = ConexaoBD.getInstance();
	Connection conn = null;
	

	//CRUD - CREATE
	public void adicionarContato(JAvaBeans contato) {
		StringBuilder sqlInsert = new StringBuilder();
		
		sqlInsert.append("INSERT INTO contatos(nome, fone, email) VALUES ");
		sqlInsert.append("(?, ?, ?);");

		try {
			//Pegar a conexão já aberta
			
			conn = conexao.getConexao();
			System.out.println(conn);
			
			//Preparar a query para ser executada no BD
			PreparedStatement pst = conn.prepareStatement(sqlInsert.toString());
			System.out.println(pst);
			
			//Substituir os parâmetros (?) pelo conteúdo das variáveis JAvaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			
			//Executar a query
			System.out.println((pst.executeUpdate()));
			
			//Encerrar a conexão com o BD
			conexao.fecharConexao();
			
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getCause());
			System.out.println("Erro na classe DAO "+e.getMessage());
		}
	}
	
	//CRUD - READ
	public ArrayList<JAvaBeans> listarContatos(){
		//Criar uma variável de ArrayList que acesse a classe JAvaBeans
		ArrayList<JAvaBeans> contatos = new ArrayList<>();
		
		StringBuilder sqlSelect = new StringBuilder();
		
		sqlSelect.append("SELECT * FROM contatos ORDER BY nome");
		
		try {
			conn = conexao.getConexao();
			
			//Preparar a query para executar no BD
			PreparedStatement pst = conn.prepareStatement(sqlSelect.toString());
			
			//Retornar o resultado da query executada no BD
			ResultSet rs = pst.executeQuery();
			
			//Imprimir todos os contatos
			while(rs.next()) {
				contatos.add(new JAvaBeans(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			
			//Fechar a conexão
			conexao.fecharConexao();
			
			return contatos;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	
	}
	
	//CRUD - UPDATE
	public void selecionarContato(JAvaBeans contato) {
		StringBuilder sqlRead = new StringBuilder();
		sqlRead.append("SELECT * FROM contatos WHERE idcon=?;");
		
		try {
			conn = conexao.getConexao();
			
			//Preparar a query
			PreparedStatement pst = conn.prepareStatement(sqlRead.toString());
			
			//Substituir o ? pelo dado
			pst.setInt(1, contato.getIdcon());
			
			//Trazer as informações do banco
			ResultSet rs = pst.executeQuery();	
			
			while(rs.next()) {
				//Setar as variáveis JAvaBeans
//				contato = new JAvaBeans(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				
				contato.setIdcon(rs.getInt(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			conexao.fecharConexao();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//CRUD - UPDATE
	public void editarContato(JAvaBeans contato) {
		StringBuilder sqlUpdate = new StringBuilder();
		
		//Inserir o comando SQL na variável sqlUpdate
		sqlUpdate.append("UPDATE contatos SET nome=?, fone=?, email=? ");
		sqlUpdate.append("WHERE idcon=?;");
		
		try {
			conn = conexao.getConexao();
			
			//Preparar a query
			PreparedStatement pst = conn.prepareStatement(sqlUpdate.toString());
			
			//Substituir as interrogações pelo conteúdos das variáveis JAvaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setInt(4, contato.getIdcon());
			
			//Executar a query
			pst.executeUpdate();
			
			//Fechar a conexão
			conexao.fecharConexao();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	//CRUD - DELETE
		public void deletarContato(JAvaBeans contato) {
			StringBuilder sqlDelete = new StringBuilder();
			
			//Inserir o comando SQL na variável sqlUpdate
			sqlDelete.append("DELETE FROM contatos WHERE idcon=?;");
			
			try {
				conn = conexao.getConexao();
				
				//Preparar a query
				PreparedStatement pst = conn.prepareStatement(sqlDelete.toString());
				
				//Substituir a interrogação
				pst.setInt(1, contato.getIdcon());
				
				//Executar a query
				pst.executeUpdate();
				
				//Fechar a conexão
				conexao.fecharConexao();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	
}
