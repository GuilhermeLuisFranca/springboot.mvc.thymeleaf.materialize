package br.com.guilherme.springboot.mvc.thymeleaf.materialize.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
/**
 * para separar que cada usuario tera acessos especificos
 */
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nomeRole;
	
	@Override
	public String getAuthority() {// ROLE_ADMIN, ROLE_GERENTE, ROLE_SECRETARIO
		return this.nomeRole;
	}

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeRole() {
		return nomeRole;
	}
	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}
	
	
	
}
