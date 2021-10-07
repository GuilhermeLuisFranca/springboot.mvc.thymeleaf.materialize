package br.com.guilherme.springboot.mvc.thymeleaf.materialize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.guilherme.springboot.mvc.thymeleaf.materialize.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

	//lista todos os usuarios ordenado por id
	@Query(value = "select u from Pessoa u ORDER BY u.id")
	List<Pessoa> listarAll();
	
	//lista os usuarios pesquisados por nome
	@Query(value = "select c from Pessoa c where upper(trim(c.nome)) like %?1% ORDER BY c.id")
	List<Pessoa> listarAllByName(String nome);

}









/*/*/