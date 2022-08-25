package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ConexaoBD;
import model.DAO;
import model.JAvaBeans;

//@WebServlet(urlPatterns = { "/Controller", "/main", "/insert" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConexaoBD conexao = ConexaoBD.getInstance();
	JAvaBeans contato = new JAvaBeans();
	DAO dao = new DAO();

	public Controller() {
		super(); // TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);

		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		}else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			deletarContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatório(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}

	// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criar um objeto que receberá os dados da classe JAvaBeans
		ArrayList<JAvaBeans> lista = dao.listarContatos(); // O objetivo é imprimir na tela os dados do BD

//		//Teste de recebimento da lista
//		for(int i=0; i<lista.size(); i++) {
//			System.out.println(lista.get(i).getIdcon());
//			System.out.println(lista.get(i).getNome());
//			System.out.println(lista.get(i).getFone());
//			System.out.println(lista.get(i).getEmail());
//		}

		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista); // Setar um atributo do documento jsp com a lista
		// Despachar a lista ao documento agenda.jsp
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		// Encaminhar o objeto lista ao documento jsp
		rd.forward(request, response);

	}

	// Novo Contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Teste de recebimento dos dados do formulário
//		System.out.println(request.getParameter("nome"));
//		System.out.println(request.getParameter("fone"));
//		System.out.println(request.getParameter("email"));

		// Setar as variáveis javaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// Invocar o método adicionarContato de DAO
		dao.adicionarContato(contato);

		// Redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}

	// Listar Contato
		protected void listarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			//Recebimento do id do contato a ser editado
			int idcon = Integer.parseInt(request.getParameter("idcon"));
			//System.out.println(idcon);
			
			//Setar a variável JAvaBeans
			contato.setIdcon(idcon);
			dao.selecionarContato(contato);
			
			//Teste de recebimento
//			System.out.println(contato.getIdcon());
//			System.out.println(contato.getNome());
//			System.out.println(contato.getFone());
//			System.out.println(contato.getEmail());
			
			//Setar os atributos do formulário com o conteúdo JAvaBeans
			request.setAttribute("idcon", contato.getIdcon());
			request.setAttribute("nome", contato.getNome());
			request.setAttribute("fone", contato.getFone());
			request.setAttribute("email", contato.getEmail());

			//Encaminhar ao documento editar.jsp
			RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
	
			rd.forward(request, response);
		}
		
		protected void editarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			//Teste de recebimento
//			System.out.println(request.getParameter("idcon"));
//			System.out.println(request.getParameter("nome"));
//			System.out.println(request.getParameter("fone"));
//			System.out.println(request.getParameter("email"));
			
			//Setar as variáveis no JAvaBeans
			contato.setIdcon(Integer.parseInt(request.getParameter("idcon")));
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
			
			//Executar o método editarContato
			dao.editarContato(contato);
			
			//Redirecionar para agenda.jsp e atualizar as alterações
			response.sendRedirect("main");
		}
		
		//Remover Contato
		protected void deletarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			int idcon = Integer.parseInt(request.getParameter("idcon"));
			
			//Teste de recebimento do id do contato
			//System.out.println(idcon);
			
			//Setar as variáveis no JAvaBeans
			contato.setIdcon(idcon);
			
			//Chamar o método deletarContato
			dao.deletarContato(contato);
			
			//Redirecionar para agenda.jsp e atualizar as alterações
			response.sendRedirect("main");
		}
		
		//Gerar relatório em PDF
		protected void gerarRelatório(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			Document documento = new Document();
			try {
				//Tipo de conteúdo
				response.setContentType("apllication/pdf");
				
				//Nome do documento
				response.addHeader("Content-Disposition", "inline; filename=" +"contatos.pdf");
				
				//Criar o documento
				PdfWriter.getInstance(documento, response.getOutputStream());
				
				//Abrir o documento para gerar o conteúdo
				documento.open();
				documento.add(new Paragraph("Lista de contatos: "));
				documento.add(new Paragraph(" "));
				
				//Criar uma tabela
				PdfPTable tabela = new PdfPTable(3); //O conteúdo passado no construtor indica a quantidade de colunas
				
				//Cabeçalho
				PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				
				//Popular a tabela com os contatos
				ArrayList<JAvaBeans> lista = dao.listarContatos(); 
				for(int i=0; i<lista.size(); i++) {
					tabela.addCell(lista.get(i).getNome());
					tabela.addCell(lista.get(i).getFone());
					tabela.addCell(lista.get(i).getEmail());
				}
				
				documento.add(tabela);	
				
				//Fechar o documento
				documento.close();
			} catch (Exception e) {
				System.out.println(e);
				documento.close();
			}
			
		}
	
	// conexao.testeConexao();
}
