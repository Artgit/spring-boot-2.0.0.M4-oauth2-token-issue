package com.decisionwanted.domain.api.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${logout.success.url}")
	private String logoutSuccessUrl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		http
			.cors()
		.and()
			.csrf().ignoringAntMatchers("/v1.0/**", "/logout")
        .and()
        	.authorizeRequests()
        	
        	.antMatchers("/oauth/authorize").authenticated()
            //Anyone can access the urls
        	.antMatchers("/images/**").permitAll()
        	.antMatchers("/signin/**").permitAll()
        	.antMatchers("/v1.0/**").permitAll()
            .antMatchers("/auth/**").permitAll()
            .antMatchers("/actuator/health").permitAll()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
        .and()
            .formLogin()
            	.loginPage("/login")
            	.loginProcessingUrl("/login")
            	.failureUrl("/login?error=true")
            	.usernameParameter("username")
            	.passwordParameter("password")
            	.permitAll()
            .and()
                .logout()
                	.logoutUrl("/logout")
	                .logoutSuccessUrl(logoutSuccessUrl)
	                .permitAll();
		// @formatter:on
	}

	/**
	 * Configures the authentication manager bean which processes authentication requests.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN").and().withUser("guest")
        .password("guest").roles("USER");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}