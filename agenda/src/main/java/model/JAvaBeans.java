package model;

public class JAvaBeans {
	private int idcon;
	private String nome;
	private String fone;
	private String email;
	
	public JAvaBeans() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JAvaBeans(int idcon, String nome, String fone, String email) {
		super();
		this.setIdcon(idcon);
		this.setNome(nome);
		this.setFone(fone);
		this.setEmail(email);
	}


	public int getIdcon() {
		return idcon;
	}


	public void setIdcon(int idcon) {
		this.idcon = idcon;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getFone() {
		return fone;
	}


	public void setFone(String fone) {
		this.fone = fone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
