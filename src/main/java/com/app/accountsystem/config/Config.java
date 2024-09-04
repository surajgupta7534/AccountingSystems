package com.app.accountsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.app.accountsystem.entity.Credentials;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Config {
	@Value("${integration.expensify.partnerUserID}")
	private String partnerUserID;
	@Value("${integration.expensify.partnerUserSecret}")
	private String partnerUserSecret;

	@Bean
	public Credentials credentials() {
		return new Credentials(partnerUserID, partnerUserSecret);
	}

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
