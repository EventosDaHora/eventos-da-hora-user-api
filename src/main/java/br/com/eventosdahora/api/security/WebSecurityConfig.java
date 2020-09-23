package br.com.eventosdahora.api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		List<String> allowed = new ArrayList<String>();
		allowed.add(CorsConfiguration.ALL);
		corsConfiguration.setAllowedHeaders(allowed);
		corsConfiguration.setAllowedMethods(allowed);
		corsConfiguration.setAllowedOrigins(allowed);

		httpSecurity.cors().configurationSource(request -> corsConfiguration);
//		httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

		httpSecurity.csrf().disable().authorizeRequests()

		.antMatchers(HttpMethod.POST,   "/login").permitAll()
		.antMatchers(HttpMethod.POST,   "/login/admin").permitAll()
		
		.antMatchers(HttpMethod.GET,	"/users").hasRole("USER_FIND_ALL")
		.antMatchers(HttpMethod.GET,	"/users/**").hasRole("USER_FIND")
        .antMatchers(HttpMethod.POST,	"/users").permitAll()
		.antMatchers(HttpMethod.PUT,	"/users").hasRole("USER_EDIT")
		.antMatchers(HttpMethod.DELETE, "/users/*").hasRole("USER_DELETE")
		.antMatchers(HttpMethod.PUT,	"/users/password/create/**/**").permitAll()
		.antMatchers(HttpMethod.PUT,	"/users/password/**").hasRole("USER_PASSWORD_EDIT")
		.antMatchers(HttpMethod.POST,	"/users/password/reset/**").permitAll()
		
        .antMatchers(HttpMethod.GET,	"/users-admin").hasRole("USER_ADMIN_FIND_ALL")
        .antMatchers(HttpMethod.GET,	"/users-admin/**").hasRole("USER_ADMIN_FIND")
        .antMatchers(HttpMethod.POST,	"/users-admin").hasRole("USER_ADMIN_SAVE")
        .antMatchers(HttpMethod.PUT,	"/users-admin/**").hasRole("USER_ADMIN_EDIT")
        .antMatchers(HttpMethod.DELETE, "/users-admin/*").hasRole("USER_ADMIN_DELETE")
        .antMatchers(HttpMethod.PUT,	"/users-admin/password/create/**/**").permitAll()
        .antMatchers(HttpMethod.PUT,	"/users-admin/password/**").hasRole("USER_ADMIN_PASSWORD_EDIT")
        .antMatchers(HttpMethod.POST,	"/users-admin/password/reset/**").permitAll()

		.antMatchers("/v2/api-docs",
				     "/configuration/ui",
				     "/swagger-resources/**",
				     "/configuration/security",
				     "/swagger-ui.html",
				     "/webjars/**",
                     "/actuator/**",
                     "/resources/**").permitAll()

			.anyRequest().authenticated()
			.and()

			// filtra outras requisições para verificar a presença do JWT no header
			.addFilterBefore(new JWTAuthenticationFilter(secret),
	                UsernamePasswordAuthenticationFilter.class);
	}
}
