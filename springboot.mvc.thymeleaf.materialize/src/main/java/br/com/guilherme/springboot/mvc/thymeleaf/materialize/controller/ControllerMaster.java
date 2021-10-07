package br.com.guilherme.springboot.mvc.thymeleaf.materialize.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.guilherme.springboot.mvc.thymeleaf.materialize.model.Pessoa;
import br.com.guilherme.springboot.mvc.thymeleaf.materialize.model.Telefone;
import br.com.guilherme.springboot.mvc.thymeleaf.materialize.repository.PessoaRepository;
import br.com.guilherme.springboot.mvc.thymeleaf.materialize.repository.TelefoneRepository;

@Controller
public class ControllerMaster {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	
	
	@GetMapping("/carregarIndex")
	/**
	 * RETORNA PARA O INDEX
	 */
	public String carregarIndex() {
		return "cadastro/carregarindex";
	}
	
	
	
	@GetMapping("/cadastroPessoa")
	/**
	 * @return para a pagina de CRUD da tabela pessoa
	 */
	public ModelAndView cadastroPessoa() {
		
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		
		view.addObject("pessoaobj", new Pessoa());//passa para a tela os dados vazios do id onde sao carregados no form gracas ao metodo de edicao
		
		view.addObject("pessoas", pessoaRepository.listarAll());//passa para a tela todos os cadastros
		
		return view;
		
	}
	
	
	
	@PostMapping("**/salvarPessoa")
	/**
	 * se houver algum erro lanca a mensagem, se tudo der certo salva o cadastro e lista todos os usuarios
	 */
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) throws IOException {
		
		pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));//antes de salvar vai carregar o objeto tel pro pessoa pra nao dar erro de cascade
		
		if(bindingResult.hasErrors()) {//se houver erro
			
			ModelAndView view = new ModelAndView("cadastro/cadastroPessoa.html");
			
			Iterable<Pessoa> pessoasIterable = pessoaRepository.listarAll();
			
			view.addObject("pessoas", pessoasIterable);//apos salvar carrega a lista
			view.addObject("pessoaobj", pessoa);

			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());//get default mostrara os erros vindos das anotacoes da classe modelo
			}
			
			view.addObject("msg", msg);
			return view;

		} //senao houver erro segue o fluxo
		
		ModelAndView view = new ModelAndView("cadastro/carregarpagpessoa");
		
		pessoaRepository.save(pessoa);
		
		return view;
		
	}
	
	
	
	@GetMapping("/listaPessoas")
	/**
	 * lista todos os cadastros, metodo apenas de demonstracao pois ao deletar ou criar/atualizar e ao abrir a pagina ja lista os cadastros
	 */
	public ModelAndView listaPessoas() {
		
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		
		Iterable<Pessoa> pessoaIterable = pessoaRepository.listarAll();
		
		view.addObject("pessoaobj", new Pessoa());//passa para a tela os dados vazios do id onde sao carregados no form gracas ao metodo de edicao
		
		view.addObject("pessoas", pessoaIterable);//passa para a tela todos os cadastros
		
		return view;
		
	}
	
	
	
	@GetMapping("/editarpessoa/{idpessoa}")
	/**
	 * pega o id e bota todos os dados deste id do banco no formulario para poder atualizar
	 */
	public ModelAndView editarPessoa(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		
		view.addObject("pessoaobj", pessoa.get());//passa para a tela os dados do id pra carrega no form
		
		return view;
		
	}
	
	
	
	@GetMapping("/deletarpessoa/{idpessoa}")
	/**
	 * pega o id e exclui todos os dados deste id em seguida lista os cadastros
	 */
	public ModelAndView deletarPessoa(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView view = new ModelAndView("cadastro/carregarpagpessoa");//esta pagina redireciona para a pagina de crud novamente
		
		pessoaRepository.deleteById(idpessoa);//deleta os dados
		
		return view;
		
	}
	
	
	
	@PostMapping("**/pesquisarpessoa")
	/**
	 * pesquisa um usario pelo nome
	 */
	public ModelAndView pesquisarPessoa(@RequestParam("nomepesquisa") String nomepesquisa) {
		
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		
		view.addObject("pessoas", pessoaRepository.listarAllByName(nomepesquisa.trim().toUpperCase()));//pesquisa
		
		view.addObject("pessoaobj", new Pessoa());//passa para a tela os dados vazios do id onde sao carregados no form gracas ao metodo de edicao
		
		return view;
		
	}
	
	
	
	@GetMapping("/telefones/{idpessoa}")
	/**
	 * resgata o id do usuario selecionado p adicionar telefone e redireciona
	 * para a pagina pronto p adicionar excluir ou editar numero, e tbm ja lista
	 * os numeros
	 */
	public ModelAndView telefones(Telefone telefone, @PathVariable("idpessoa") Long idPessoa) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		
		Pessoa carregarTelefones = pessoaRepository.findById(idPessoa).get();
		telefone.setPessoa(carregarTelefones);
		
		modelAndView.addObject("telefoneobj", carregarTelefones);//passa objeto pra mostar na tabela
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(carregarTelefones.getId()));//carrega na tabela
		
		return modelAndView;
		
	}
	
	
	
	@PostMapping("addFonePessoa/{pessoaid}")
	/**
	 * grava a o telefone em um pra muito p algum usuario
	 */
	public ModelAndView addFonePessoa(@Valid Telefone telefone, BindingResult bindingResult, @PathVariable("pessoaid") Long pessoaid) throws IOException {	
	
		if(bindingResult.hasErrors()) {//se houver erro

			ModelAndView modelAndView = new ModelAndView("cadastro/telefones.html");

			Pessoa pessoa = pessoaRepository.findById(pessoaid).get();

			modelAndView.addObject("telefoneobj", pessoa);
			modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
			
			
			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());//get default mostrara os erros vindos das anotacoes da classe modelo
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
			
		}
		
		//pega o id do usuario da tabela pessoa e passa pra tabela telefone e salva
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones.html");
		modelAndView.addObject("telefoneobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
		
		
		return modelAndView;
		
	}
	
	
	
	@GetMapping("/deletartelefone/{telefoneid}")
	/**
	 * deleta um telefone
	 */
	public ModelAndView deletarTelefone(@PathVariable("telefoneid") Long telefoneid) {
		
		Pessoa pessoa = telefoneRepository.findById(telefoneid).get().getPessoa();//carregando e retornando objeto telefone
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones.html");//retorna pra tela novamente
		
		telefoneRepository.deleteById(telefoneid);//deleta
		
		modelAndView.addObject("telefoneobj", pessoa);//passa objeto pra mostar na tabela
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));//carrega na tabela

		
		return modelAndView;
		
	}
	
}
