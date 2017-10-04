package com.decisionwanted.domain.api.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2ServerConfig {

	public static final String RESOURCE_ID = "restservice";
	public static final String DECISIONWANTED_CLIENT_ID = "decisionwanted_client_id";

	@Value("${jwt.access.token.converter.signing.key}")
	private String jwtAccessTokenConverterSigningKey;

	@Value("${jwt.access.token.validity.seconds}")
	private int accessTokenValiditySeconds;

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
		return tokenServices;
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		converter.setSigningKey(jwtAccessTokenConverterSigningKey);

		DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
		//userTokenConverter.setUserDetailsService(userDetailsService);
		accessTokenConverter.setUserTokenConverter(userTokenConverter);

		converter.setAccessTokenConverter(accessTokenConverter);

		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Value("${jwt.access.token.validity.seconds}")
		private int accessTokenValiditySeconds;

		@Autowired
		private TokenStore tokenStore;

		@Autowired
		private TokenEnhancer tokenEnhancer;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			// @formatter:off
			endpoints
				.tokenStore(tokenStore)
				.tokenEnhancer(tokenEnhancer)
				.authenticationManager(this.authenticationManager);
			// @formatter:on
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// @formatter:off
			clients
				.inMemory()
					.withClient("clientapp")
						.authorizedGrantTypes("password","refresh_token")
						.authorities("ROLE_CLIENT")
						.scopes("read", "write")
						.resourceIds(RESOURCE_ID)
						.secret("CHANGEIT")
					.and()
					.withClient(DECISIONWANTED_CLIENT_ID)
						.authorizedGrantTypes("implicit")
						.scopes("read", "write")
						.autoApprove(true)
					.and()
			            .withClient("my-trusted-client")
			            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
			            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
			            .scopes("read", "write", "trust")
			            .accessTokenValiditySeconds(accessTokenValiditySeconds);
			// @formatter:on
		}

	}

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Autowired
		private ResourceServerTokenServices tokenService;

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			// @formatter:off
			resources			
				.resourceId(RESOURCE_ID)
				.tokenServices(tokenService);
			// @formatter:on
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http				
				.antMatcher("/v1.0/**").authorizeRequests()
				.antMatchers("/v1.0/search/**").permitAll()
				.antMatchers("/v1.0/users/**").permitAll()
				.antMatchers("/v1.0/decisions/**").permitAll()
				.antMatchers("/v1.0/votes/**").permitAll()
				.antMatchers("/v1.0/values/**").permitAll()
				.antMatchers("/v1.0/likes/**").permitAll()
				.antMatchers("/v1.0/likeables/**").permitAll()
				.antMatchers("/v1.0/flags/**").permitAll()
				.antMatchers("/v1.0/flagtypes/**").permitAll()
				.antMatchers("/v1.0/flaggables/**").permitAll()
				.antMatchers("/v1.0/comments/**").permitAll()
				.antMatchers("/v1.0/commentables/**").permitAll()
				.antMatchers("/v1.0/subscribables/**").permitAll()
				.antMatchers("/v1.0/favoritables/**").permitAll()
				.antMatchers("/v1.0/import/**").permitAll()
				.antMatchers("/v1.0/medias/**").permitAll()
				.antMatchers("/swagger**").permitAll()
				.anyRequest().authenticated()
				.and()
					.cors()
				.and()
					.csrf().disable()
				.sessionManagement()
					.sessionCreationPolicy(STATELESS); 
			// @formatter:on
		}

	}

}