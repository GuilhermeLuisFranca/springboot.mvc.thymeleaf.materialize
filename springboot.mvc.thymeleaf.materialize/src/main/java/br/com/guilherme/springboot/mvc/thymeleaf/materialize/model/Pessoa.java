package br.com.guilherme.springboot.mvc.thymeleaf.materialize.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.guilherme.springboot.mvc.thymeleaf.materialize.enums.Cargo;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Nome não pode ser nulo")
	@NotEmpty(message = "Nome não pode estar vazio")
	@Column(length = 15)
	private String nome;
	
	@NotNull(message = "Sobrenome não pode ser nulo")
	@NotEmpty(message = "Sobrenome não pode estar vazio")
	@Column(length = 30)
	private String sobrenome;

	/*esta expressao nao esta muito boa por isso fiz uma validacao bem melhor no controller, so deixei aqui pra nao joga fora
	@Email(message = "E-Mail não valido")
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$", message = "E-Mail não valido")*/
	@NotNull(message = "E-Mail não pode ser nulo")
	@NotEmpty(message = "E-Mail não pode estar vazio")
	@Column(length = 60, unique = true)
	private String email;
	
	@NotNull(message = "Sexo não pode ser nulo")
	@NotEmpty(message = "Sexo não pode estar vazio")
	private String sexo;
	
	@Min(value = 18, message = "Menor de Idade nao pode")
	@Max(value = 100, message = "Mais de 100 anos han? insere uma idade correta ai!")
	@NotNull(message = "Idade não pode ser nulo")
	private int idade;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Enumerated(EnumType.STRING)
	private Cargo cargo;
	
	@ManyToOne/*muitas pessoa podem ter esta profission*/
	private Profissao profissao;

	@OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)/*1 pra muitos, orphan e cascade para consultar tudo pra ser possivel fazer alteracoes
	que nao sejam bloquedas gracas ao relacionamento de tabela*/
	private List<Telefone> telefones;
	
	private String cep, rua, bairro, cidade, uf, ibge;//cep, toda a validacao do cep pra carregar um correto ja esta sendo feito no front usando web service
	
	@Lob
	private byte[] arquivo;
	
	private String nomeFileArquivo, tipoFileArquivo;
	
	@Column(nullable = true, columnDefinition = "TEXT", length = 10000)
	private String imagem;

	
	//getters and setters
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
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public Profissao getProfissao() {
		return profissao;
	}
	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
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
	public byte[] getArquivo() {
		return arquivo;
	}
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	public String getNomeFileArquivo() {
		return nomeFileArquivo;
	}
	public void setNomeFileArquivo(String nomeFileArquivo) {
		this.nomeFileArquivo = nomeFileArquivo;
	}
	public String getTipoFileArquivo() {
		return tipoFileArquivo;
	}
	public void setTipoFileArquivo(String tipoFileArquivo) {
		this.tipoFileArquivo = tipoFileArquivo;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	
	
	
}
