package com.decisionwanted.domain.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@EnableAsync
@Configuration
public class ApplicationConfig {

	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.noOpText();
	}

}