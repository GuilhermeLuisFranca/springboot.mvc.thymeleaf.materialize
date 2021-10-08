package br.com.guilherme.springboot.mvc.thymeleaf.materialize.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Nome não pode ser nulo")
	@NotEmpty(message = "Nome não pode estar vazio")
	private String nome;
	
	@NotNull(message = "Sobrenome não pode ser nulo")
	@NotEmpty(message = "Sobrenome não pode estar vazio")
	private String sobrenome;

	@NotNull(message = "E-Mail não pode ser nulo")
	@NotEmpty(message = "E-Mail não pode estar vazio")
	private String email;
	
	@NotNull(message = "Sexo não pode ser nulo")
	@NotEmpty(message = "Sexo não pode estar vazio")
	private String sexo;
	
	
	@Min(value = 18, message = "Menor de Idade nao pode")
	@Max(value = 100, message = "Mais de 100 anos han? insere uma idade correta ai!")
	@NotNull(message = "Idade não pode ser nulo")
	private int idade;
	
	@ManyToOne/*muitas pessoa podem ter esta profission*/
	private Profissao profissao;

	@OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)/*1 pra muitos, orphan e cascade para consultar tudo pra ser possivel fazer alteracoes
	que nao sejam bloquedas gracas ao relacionamento de tabela*/
	private List<Telefone> telefones;
	
	private String cep, rua, bairro, cidade, uf, ibge;//cep, toda a validacao do cep pra carregar um correto ja esta sendo feito no front usando web service
	
	
	
	//getters and setters
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
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
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getIbge() {
		return ibge;
	}
	public void setIbge(String ibge) {
		this.ibge = ibge;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Profissao getProfissao() {
		return profissao;
	}
	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}
	
	
}
