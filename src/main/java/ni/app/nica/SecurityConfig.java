package ni.app.nica;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception{
        http.formLogin(login -> login.authenticationSuccessHandler(
                new RedirectServerAuthenticationSuccessHandler("/graphiql")
        ));
        
		return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(	ex -> {
							ex.anyExchange().authenticated();
						})
				.build();
	}
	
	@Bean
	MapReactiveUserDetailsService userDetailsService() {
		@SuppressWarnings("deprecation")
		User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
		UserDetails admin = userBuilder
				.username("MALEMAN")
				.password("P@55w0RD")
				.roles("USER","ADMIN")
				.build();
		
		UserDetails usr1 = userBuilder
				.username("USR1")
				.password("123")
				.roles("USER")
				.build();
		
		return new MapReactiveUserDetailsService(admin, usr1);
		
	}
}
