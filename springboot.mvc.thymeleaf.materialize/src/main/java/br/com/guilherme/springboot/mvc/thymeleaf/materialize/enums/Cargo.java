package br.com.guilherme.springboot.mvc.thymeleaf.materialize.enums;

public enum Cargo {

	ESTAGIARIO("Estagiário"),
	JUNIOR("Júnior"),
	PLENO("Pleno"),
	SENIOR("Senior"),
	GERENTE("Gerente de Projetos"),
	CHEFE("Chefe");
	
	private String nome;
	
	private Cargo(String nome) {
		this.nome = nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return this.name();
	}
	
}
