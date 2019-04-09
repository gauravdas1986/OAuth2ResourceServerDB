package com.oauth2.Oauth2Client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableResourceServer
@SpringBootApplication
@RestController
public class Oauth2ClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ClientApplication.class, args);
	}

	@Bean
	@ConfigurationProperties("security.oauth2.client")
	protected ClientCredentialsResourceDetails oAuthDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	protected OAuth2RestTemplate restTemplate() {
		return new OAuth2RestTemplate(oAuthDetails());
	}

	@Override
	public void run(String... args) {
		System.out.println("RESPONSE TOKEN " + restTemplate().getAccessToken());
		// .postForEntity("http://localhost:8080/oauth/token", String.class));
	}

	@RequestMapping("/private")
	public String test() {
		return "THIS IS RESOURCE SERVER PRIVATE PAGE";
	}

	 @Configuration
	 @EnableGlobalMethodSecurity(prePostEnabled = true)
	 public static class MethodSecurityConfig extends
	 GlobalMethodSecurityConfiguration {
	 @Override
	 protected MethodSecurityExpressionHandler createExpressionHandler() {
	 return new OAuth2MethodSecurityExpressionHandler();
	 }
	 }
}