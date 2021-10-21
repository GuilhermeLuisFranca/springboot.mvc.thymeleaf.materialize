package br.com.guilherme.springboot.mvc.thymeleaf.materialize.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.guilherme.springboot.mvc.thymeleaf.materialize.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	//lista todos os usuarios ordenado por id
	@Query(value = "select u from Pessoa u ORDER BY u.id")
	List<Pessoa> listarAll();
	
	//lista os usuarios pesquisados por nome - nao mais usado, deixando aqui apenas para consulta - 
	//forma passada pra tela: view.addObject("pessoas", pessoaRepository.listarAllByName(nomepesquisa.trim().toUpperCase()));
	@Query(value = "select c from Pessoa c where upper(trim(c.nome)) like %?1% ORDER BY c.id")
	List<Pessoa> listarAllByName(String nome);
			
	//todos os dados de um determinado id
	@Query(value = "SELECT m.email FROM Pessoa m WHERE m.id = ?1")
	String emailById(Long id);
			
	//verificando se ja existe o email passado
	@Query(value = "SELECT m FROM Pessoa m WHERE m.email = ?1")
	List<Pessoa> verificarEmail(String email);
 
	//lista os usuarios pesquisados por nome usando paginacao
	default Page<Pessoa> listarAllByNamePage(String nome, Pageable pageable) {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		
		//configurando pesquisa para consultar por partes do nome no banco de dados, igual a like por sql
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny().withMatcher("nome", 
				ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());//contains() faz o mesmo que trim() e ignoreCase o mesmo que toUpperCase()
		
		Example<Pessoa> example = Example.of(pessoa, ignoringExampleMatcher);
		
		Page<Pessoa> pessoas = findAll(example, pageable);
		
		return pessoas;
		
	}
	
	

}









/*/*/