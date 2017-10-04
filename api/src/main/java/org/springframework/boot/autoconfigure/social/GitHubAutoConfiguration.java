package org.springframework.boot.autoconfigure.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.impl.GitHubTemplate;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Social
 * connectivity with GitHub.
 *
 */
@Configuration
@ConditionalOnClass({ SocialConfigurerAdapter.class, GitHubConnectionFactory.class })
@ConditionalOnProperty(prefix = "spring.social.github.", value = "app-id")
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class GitHubAutoConfiguration {

	@Configuration
	@EnableSocial
	@EnableConfigurationProperties(GitHubProperties.class)
	@ConditionalOnWebApplication
	protected static class GitHubAutoConfigurationAdapter extends SocialAutoConfigurerAdapter {

		@Autowired
		private GitHubProperties properties;

		@Bean
		@ConditionalOnMissingBean(GitHub.class)
		@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
		public GitHub github(ConnectionRepository repository) {
			Connection<GitHub> connection = repository.findPrimaryConnection(GitHub.class);
			return connection != null ? connection.getApi() : new GitHubTemplate();
		}

		@Bean(name = { "connect/githubConnect", "connect/githubConnected" })
		@ConditionalOnProperty(prefix = "spring.social.", value = "auto-connection-views")
		public View githubConnectView() {
			return new GenericConnectionStatusView("github", "GitHub");
		}

		@Override
		protected ConnectionFactory<?> createConnectionFactory() {
			GitHubConnectionFactory factory = new GitHubConnectionFactory(this.properties.getAppId(), this.properties.getAppSecret());
			return factory;
		}

	}

}