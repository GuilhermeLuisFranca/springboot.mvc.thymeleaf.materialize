package br.com.guilherme.springboot.mvc.thymeleaf.materialize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.guilherme.springboot.mvc.thymeleaf.materialize.generic.Generic;

@SpringBootApplication
@EntityScan(basePackages = "br.com.guilherme.springboot.mvc.thymeleaf.materialize.model")//diz para escanear tudo neste pacote como uma entidade de modelo
@ComponentScan(basePackages =  "br.com.guilherme.springboot.mvc.thymeleaf.materialize.*")//mapeando todos os pacotes
@EnableJpaRepositories(basePackages = {"br.com.guilherme.springboot.mvc.thymeleaf.materialize.repository"})
@EnableTransactionManagement
@EnableWebMvc
public class Application implements WebMvcConfigurer {

	
	//execucao do sistema
	//*
	public static void main(String[] args) throws Exception {
		
		Generic.openSource();
		
		SpringApplication.run(Application.class, args);
		
		Generic.coffee();
		
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
	}
	
	//*/
	
	
	
	/*execucao rapida de criptografia
	public static void main(String[]args){for(int y=0;y<10;y++){BCryptPasswordEncoder x=new BCryptPasswordEncoder();String s="adsxfdzd",t=x.encode(s),r="Criptografia ",p=" = '",o="';",c=r+y+p+t+o;System.out.println(c);}}
	
	
	
	
	//printa no console varias querys para usar no pessoa, e tbm deleta
	/*
	public static void main(String[] args) {
		for (int i = 2000; i < 2080; i++) {
			//execute este para inserir
			 System.out.println("INSERT INTO pessoa(id, email, idade, nome, sobrenome, bairro, cep, cidade, ibge, rua, uf, sexo, profissao_id, cargo, data_nascimento)VALUES \n" + "("+i+", 'emailexample"+i+"@gmail.com', 18, 'name', 'subname', 'pos', '15897-9', 'city', '6544654', 'streeth', 'PR','MASCULINO', 1, 'GERENTE', '2003-01-12');\n");
			
			//execute este caso queira deletar
			// System.out.println("DELETE FROM pessoa\n" + " WHERE id = "+i+";");
		}
	}//*/

}
