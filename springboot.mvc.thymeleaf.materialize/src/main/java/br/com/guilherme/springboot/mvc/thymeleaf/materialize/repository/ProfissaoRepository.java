package br.com.guilherme.springboot.mvc.thymeleaf.materialize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.guilherme.springboot.mvc.thymeleaf.materialize.model.Profissao;

@Repository
@Transactional
public interface ProfissaoRepository extends CrudRepository<Profissao, Long> {

	//lista todos os usuarios ordenado por id
	@Query(value = "select u from Profissao u ORDER BY u.id")
	List<Profissao> listarAll();
	
	
	
	
}
