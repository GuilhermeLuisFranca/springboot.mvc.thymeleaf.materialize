package br.com.guilherme.springboot.mvc.thymeleaf.materialize.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Profissao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String nome;

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
}
