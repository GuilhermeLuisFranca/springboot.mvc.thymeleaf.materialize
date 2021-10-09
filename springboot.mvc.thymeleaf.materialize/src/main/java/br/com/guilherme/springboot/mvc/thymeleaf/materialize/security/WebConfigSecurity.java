package br.com.guilherme.springboot.mvc.thymeleaf.materialize.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	@Override // Configura as solicitações de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()//Desativa as configurações padrão de memória.
		.authorizeRequests()//Pertimir restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll()//Qualquer usuário acessa as paginas que nao estiverem em uma pasta
		.antMatchers("admin",
				"/admin", 			
				"**/admin",			//nao e preciso
				"admin/",			//especificar tao
				"admin/**",			//bem, fiz apenas
				"/admin/",			//para ter 100% de
				"**/admin/",		//certeza
				"/admin/**",
				"**/admin/**")
			.hasAnyRole("ADMIN")//Mapeia com /admin em todos os http que quer que apenas admin tenha acesso
		.anyRequest().authenticated()
		.and().formLogin().permitAll()//permite qualquer usuário
		.loginPage("/login")//diz qual a pagina de login
		.defaultSuccessUrl("/carregarIndex")//se passar pelo login entra aqui
		.failureUrl("/login?error=true")//se falhar login vai pra pagina de login e passa o parametro de erro no login
		.and().logout().logoutSuccessUrl("/login")//Mapeia URL de Logout e invalida usuário autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	
	}
	
	
	@Override // Cria autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implementacaoUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		/*
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.withUser("admin")
		.password("$2a$10$hiG9rhz0PDQCAz0h6.RqoOwYAGXFzFGYc0iSepsgujnX8DgKLRWjG")
		.roles("ADMIN");*/
	}
	
	@Override // Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");//url padra a ser ignorada
	}

}