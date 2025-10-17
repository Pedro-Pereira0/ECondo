package com.eCondo.auth.configuration;

import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(securedEnabled=true, jsr250Enabled=true)
@EnableConfigurationProperties
@RequiredArgsConstructor
public class SecurityConfig {
    //Repo
    //private final UserRepository userRepo;
    @Value("${jwt.private.key}")
    private RSAPrivateKey rsaprivateKey;

    @Value("classpath:rsa.public.key")
    private RSAPublicKey rsaPublicKey;

    @Value("${springdoc.api-docs.path}")
	private String restApiDocPath;

	@Value("${springdoc.swagger-ui.path}")
	private String swaggerPath;

    
    
    
}
