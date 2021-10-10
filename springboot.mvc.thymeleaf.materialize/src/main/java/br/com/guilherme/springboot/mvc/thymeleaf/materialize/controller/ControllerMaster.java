package br.com.guilherme.springboot.mvc.thymeleaf.materialize.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.guilherme.springboot.mvc.thymeleaf.materialize.model.Pessoa;
import br.com.guilherme.springboot.mvc.thymeleaf.materialize.model.Telefone;
import br.com.guilherme.springboot.mvc.thymeleaf.materialize.repository.PessoaRepository;
import br.com.guilherme.springboot.mvc.thymeleaf.materialize.repository.ProfissaoRepository;
import br.com.guilherme.springboot.mvc.thymeleaf.materialize.repository.TelefoneRepository;
import br.com.guilherme.springboot.mvc.thymeleaf.materialize.service.ReportUtil;

@Controller
public class ControllerMaster {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private ReportUtil reportUtil;
	
	@Autowired
	private ProfissaoRepository profissaoRepository;
	
	@GetMapping("/carregarIndex")
	/**
	 * RETORNA PARA O INDEX
	 */
	public String carregarIndex() {
		return "cadastro/carregarindex";
	}
	
	
	
	@GetMapping("**/admin/cadastroPessoa")
	/**
	 * @return para a pagina de CRUD da tabela pessoa
	 */
	public ModelAndView cadastroPessoa() {
		
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		
		view.addObject("pessoaobj", new Pessoa());//passa para a tela os dados vazios do id onde sao carregados no form gracas ao metodo de edicao
		
		view.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("id"))));//passa para a tela todos os cadastros
		
		view.addObject("profissoes", profissaoRepository.listarAll());//lista todas as profissoes
		
		return view;
		
	}
	
	
	
	@GetMapping("**/admin/pessoaspag")
	/**
	 * 
	 * @param pageable - faz a paginacao a cada 5 pessoas
	 * 
	 * @param view - setando direto em modelo de visualizacao
	 * 
	 * @param nomepesquisa - para na hora da paginacao se estiver em modo 
	 * paginando pela pesquisa paginar corretamente
	 * 
	 * @return pra tela paginando sempre ordenado por id
	 */
	public ModelAndView carregaPessoaPorPaginacao(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable, ModelAndView view, 
			@RequestParam("nomepesquisa") String nomepesquisa) {
		
		Page<Pessoa> pagePessoa = pessoaRepository.listarAllByNamePage(nomepesquisa, pageable);
		view.addObject("pessoas", pagePessoa);//passa a lista
		
		view.addObject("pessoaobj", new Pessoa());//passa para a tela os dados vazios do id onde sao carregados no form gracas ao metodo de edicao
		
		view.addObject("nomepesquisa", nomepesquisa);//retorna o nome pesquisa no get na hora de selecionar uma paginacao
		
		view.addObject("profissoes", profissaoRepository.listarAll());//lista todas as profissoes
		
		view.setViewName("cadastro/cadastroPessoa");
		
		return view;
		
	}
	
	
	
	@PostMapping(value =  "**/admin/salvarPessoa", consumes = {"multipart/form-data"})
	/**
	 * se houver algum erro lanca a mensagem, se tudo der certo salva o cadastro e lista todos os usuarios ordenado por id e paginado
	 * 
	 * @param pessoa - carrega este modelo direto do banco e ja carregando sua validacao
	 * 
	 * @param bindingResult - faz a validacao
	 * 
	 * @param file - para poder fazer o upload de algum arquivo em byte no banco
	 * 
	 * @return sempre para a tela de cadastro
	 */
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult, final MultipartFile file) throws IOException {	
		
		pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));//antes de salvar vai carregar o objeto tel pro pessoa pra nao dar erro de cascade
		
		
		
		//se houver erros vindo da validacao do modelo
		if(bindingResult.hasErrors()) {
			
			ModelAndView view = new ModelAndView("cadastro/cadastroPessoa.html");
			
			Iterable<Pessoa> pessoasIterable = pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("id")));//passa para a tela todos os cadastros
			
			view.addObject("pessoas", pessoasIterable);//apos salvar carrega a lista
			view.addObject("pessoaobj", pessoa);
			view.addObject("profissoes", profissaoRepository.listarAll());//lista todas as profissoes

			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());//get default mostrara os erros vindos das anotacoes da classe modelo
			}
			
			view.addObject("msg", msg);
			return view;

		}
		
		
		
		//se ja existir alguma pessoa com o mesmo login
		if(pessoaRepository.verificarEmail(pessoa.getEmail()) != null && !pessoaRepository.verificarEmail(pessoa.getEmail()).isEmpty()) {
			ModelAndView view = new ModelAndView("cadastro/cadastroPessoa.html");
			
			Iterable<Pessoa> pessoasIterable = pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("id")));//passa para a tela todos os cadastros
			
			view.addObject("pessoas", pessoasIterable);//apos salvar carrega a lista
			view.addObject("pessoaobj", pessoa);
			view.addObject("profissoes", profissaoRepository.listarAll());//lista todas as profissoes
			
			view.addObject("msg", "Este E-Mail ja foi usado!");
			
			
			return view;
		}
		
		
		
		if(file.getSize() > 0) {//se tiver arquivo faz o upload
			
			pessoa.setArquivo(file.getBytes());
			
			pessoa.setTipoFileArquivo(file.getContentType());
			pessoa.setNomeFileArquivo(file.getOriginalFilename());
			
		} else {//se nao tiver pega o ja existente em caso de edicao
			if(pessoa.getId() != null && pessoa.getId() > 0) {//editando
				
				Pessoa pessoaTempo = pessoaRepository.findById(pessoa.getId()).get();
				
				pessoa.setArquivo(pessoaTempo.getArquivo());
				pessoa.setTipoFileArquivo(pessoaTempo.getTipoFileArquivo());
				pessoa.setNomeFileArquivo(pessoaTempo.getNomeFileArquivo());
				
			}
		}
		
		//pra nao perder a imagem
		Pessoa pessoaTempo = pessoaRepository.findById(pessoa.getId()).get();
		pessoa.setImagem(pessoaTempo.getImagem());
		
		ModelAndView view = new ModelAndView("cadastro/carregarpagpessoa");
		
		pessoaRepository.save(pessoa);//salva os dados
		
		return view;
		
	}
	
	
	
	@GetMapping("**/admin/listaPessoas")
	/**
	 * lista todos os cadastros e @return pra tela de cadastro
	 */
	public ModelAndView listaPessoas() {
		
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		
		Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("id")));//passa para a tela todos os cadastros
		
		view.addObject("pessoaobj", new Pessoa());//passa para a tela os dados vazios do id onde sao carregados no form gracas ao metodo de edicao
		
		view.addObject("pessoas", pessoaIterable);//passa para a tela todos os cadastros
		
		view.addObject("profissoes", profissaoRepository.listarAll());//lista todas as profissoes
		
		return view;
		
	}
	
	
	
	@GetMapping("**/admin/editarpessoa/{idpessoa}")
	/**
	 * pega o id e bota todos os dados deste id do banco no formulario para poder atualizar
	 * @param idpessoa - carrega direto o id da tela da pessoa para carregar em tela
	 * 
	 * @return pra tela de cadastro carregando os dados
	 */
	public ModelAndView editarPessoa(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);//pega este id
		
		view.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("id"))));//lista e pagina
		
		view.addObject("pessoaobj", pessoa.get());//passa para a tela os dados do id pra carrega no form
		
		view.addObject("profissoes", profissaoRepository.listarAll());//lista todas as profissoes
		
		return view;
		
	}
	
	
	
	@GetMapping("**/admin/imagem/{idpessoa}")
	/**
	 *
	 * @param pega o id da pessoa
	 * 
	 * @return pra pagina para adicionar imagem ao usuario
	 */
	public ModelAndView carregarImagem(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView view = new ModelAndView("cadastro/editarImg");
		
		Optional<Pessoa> img = pessoaRepository.findById(idpessoa);//pega este id
		
		view.addObject("pessoaobj", img.get());//passa para a tela os dados do id pra carrega no form
		
		return view;
		
	}
	
	
	
	@PostMapping(value = "**/admin/FileUpload") /* mapeia a url */
	@ResponseBody/* Descricao da resposta */
	/**
	 * faz upload da imagem em base64 para o pessoa e sem comprometer os outros dados
	 */
	public ResponseEntity<Pessoa> salvarImagem(@RequestBody Pessoa pessoa) {//Recebe os dados para salvar
		
		//antes de salvar vai carregar o objeto tel pro pessoa pra nao dar erro de cascade
		pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));
		
		//carrega todos os dados so nao a imagem, serve para quando atualizar a
		//imagem nao ser perdido esses dados
		Pessoa pessoaTempo = pessoaRepository.findById(pessoa.getId()).get();
		pessoa.setArquivo(pessoaTempo.getArquivo());
		pessoa.setTipoFileArquivo(pessoaTempo.getTipoFileArquivo());
		pessoa.setNomeFileArquivo(pessoaTempo.getNomeFileArquivo());
		pessoa.setBairro(pessoaTempo.getBairro());
		pessoa.setCargo(pessoaTempo.getCargo());
		pessoa.setCep(pessoaTempo.getCep());
		pessoa.setCidade(pessoaTempo.getCep());
		pessoa.setDataNascimento(pessoaTempo.getDataNascimento());
		pessoa.setEmail(pessoaTempo.getEmail());
		pessoa.setIbge(pessoaTempo.getIbge());
		pessoa.setIdade(pessoaTempo.getIdade());
		pessoa.setNome(pessoaTempo.getNome());
		pessoa.setRua(pessoaTempo.getRua());
		pessoa.setSexo(pessoaTempo.getSexo());
		pessoa.setSobrenome(pessoaTempo.getSobrenome());
		pessoa.setUf(pessoaTempo.getUf());
		
		//salva os dados, todos foram carregado n front e esta sendo carregado novamente aqui, so nao o arquivo
		Pessoa pessoaImg = pessoaRepository.saveAndFlush(pessoa);
		
		return new ResponseEntity<Pessoa>(pessoaImg, HttpStatus.CREATED);
		
	}
	
	
	
	@GetMapping("**/admin/deletarpessoa/{idpessoa}")
	/**
	 * pega o id e exclui todos os dados deste id em seguida lista os cadastros
	 * 
	 * @param idpessoa - pega o id da pessoa pra poder deletar
	 * 
	 * @return pra uma tela que retorna para o metodo que carrega a tela de cadastro
	 */
	public ModelAndView deletarPessoa(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView view = new ModelAndView("cadastro/carregarpagpessoa");//esta pagina redireciona para a pagina de crud novamente
		
		pessoaRepository.deleteById(idpessoa);//deleta os dados
		
		return view;
		
	}
	
	
	
	@PostMapping("**/admin/pesquisarpessoa")
	/**
	 * pesquisa um usario pelo nome ordenado por id
	 * 
	 * @param nomepesquisa - carrega o nome pesquisado para fazer a consulta
	 * 
	 * @param pageable - carrega a paginacao a cada 5 pessoas e ordenado pelo id
	 * 
	 * @return pra tela de cadastro carregando os dados
	 */
	public ModelAndView pesquisarPessoa(@RequestParam("nomepesquisa") String nomepesquisa, @PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
		
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		
		Page<Pessoa> pessoas = pessoaRepository.listarAllByNamePage(nomepesquisa, pageable);
		view.addObject("pessoas", pessoas);//lista e pagina
		
		view.addObject("pessoaobj", new Pessoa());//passa para a tela os dados vazios do id onde sao carregados no form gracas ao metodo de edicao
		
		view.addObject("nomepesquisa", nomepesquisa);//passa para a tela os dados vazios do id onde sao carregados no form gracas ao metodo de edicao
		
		view.addObject("profissoes", profissaoRepository.listarAll());//lista todas as profissoes
		
		return view;
		
	}
	
	
	
	@GetMapping("**/admin/imprimirRelatorio")
	/**
	 * @return um download de relatorio do tipo jasper com algumas informacoes de alguns usuarios
	 */
	public void imprimePdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
			
		Iterable<Pessoa> iterator = pessoaRepository.listarAll();
		
		for (Pessoa pessoa : iterator) {
			pessoas.add(pessoa);
		}
		
		//chamar o serviço que faz a geraçao do relatorio
		byte[] pdf = reportUtil.gerarRelatorio(pessoas, "pessoa", request.getServletContext());
		
		//tamanho da resposta para o navegador
		response.setContentLength(pdf.length);
		
		//definir na resposta o tipo de arquivo
		response.setContentType("application/octet-stream");
		
		//difinir o cabecalho da resposta
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		response.setHeader(headerKey, headerValue);
		
		//finaliza a resposta pro navegador
		response.getOutputStream().write(pdf);
		
	}
	
	
	
	@GetMapping("**/admin/baixarArquivo/{idpessoa}")
	/**
	 * pega o id e baixa o arquivo deste id
	 * 
	 * @param idpessoa - pega o id da pessoa pra poder buscar o seu arquivo no banco
	 * 
	 * @param response - pra poder retorna a resposta que no caso e uma resposta do download
	 * 
	 * @throws IOException 
	 */
	public void baixarArquivo(@PathVariable("idpessoa") Long idpessoa, HttpServletResponse response) throws IOException {
		
		//consultar o objeto pessoa no banco de dados
		Pessoa pessoa = pessoaRepository.findById(idpessoa).get();
		if(pessoa.getArquivo() != null) {//se exitir arquivo pra fazer download
			
			//setar tamanho da resposta
			response.setContentLength(pessoa.getArquivo().length);
			
			//tipo do arquivo para download ou pode ser generica application/octet-stream
			response.setContentType(pessoa.getTipoFileArquivo());
			
			//define o cabecalho da resposta
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", pessoa.getNomeFileArquivo());
			response.setHeader(headerKey, headerValue);
			
			//finaliza a resposta passando o arquivo
			response.getOutputStream().write(pessoa.getArquivo());
			
		}
		
	}
	
	
	
	@GetMapping("**/admin/telefones/{idpessoa}")
	/**
	 * resgata o id do usuario selecionado p adicionar telefone e redireciona
	 * para a pagina pronto p adicionar excluir ou editar numero, e tbm ja lista
	 * os numeros
	 * 
	 * @param telefone - carregando a classe telefone
	 * 
	 * @param idPessoa - resgata o id da pessoa passado
	 * 
	 * @return pra tela de telefones adicionando informacoes basicas da pessoa 
	 * passada atraves do @param idPessoa em seguida faz uma listagem dos telefones
	 * desta pessoa
	 */
	public ModelAndView telefones(Telefone telefone, @PathVariable("idpessoa") Long idPessoa) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		
		Pessoa carregarTelefones = pessoaRepository.findById(idPessoa).get();
		telefone.setPessoa(carregarTelefones);
		
		modelAndView.addObject("telefoneobj", carregarTelefones);//passa objeto pra mostar na tabela
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(carregarTelefones.getId()));//carrega na tabela
		
		return modelAndView;
		
	}
	
	
	
	@PostMapping("**/admin/addFonePessoa/{pessoaid}")
	/**
	 * 
	 * @param telefone - carregando a classe ja puxando as validacoes la feitas
	 * 
	 * @param bindingResult - sera usada pra verificar se acontecer algum erro
	 * vindo do @param telefone
	 * 
	 * @param pessoaid - resgata o id da pessoa passado
	 * 
	 * if houver erro de validacao @return pra tela avisando
	 * else @return salva este novo telefone, lembrando que a tabela e em @OneToMany
	 * entao pode-se salvar varios telefones pra apenas uma pessoa
	 * 
	 * @throws IOException
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
		
		//pega o id da pessoa da tabela pessoa e passa pra tabela telefone e salva
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones.html");
		modelAndView.addObject("telefoneobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
		
		
		return modelAndView;
		
	}
	
	
	
	@GetMapping("**/admin/deletartelefone/{telefoneid}")
	/**
	 * @param telefoneid - resgatamos o id do telefone da pessoa
	 * 
	 * @return pra tela e deleta
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
