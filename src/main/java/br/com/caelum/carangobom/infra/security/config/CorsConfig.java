package br.com.caelum.carangobom.infra.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
//	@Value("${carango-bom-api.cors.allowed.origin}") 
	private String corsAllowedOrigin;

//	@Value("${carango-bom-api.cors.allowed.methods}") 
	private List<String> corsAllowedMethods;
	
//	@Value("${carango-bom-api.cors.allow-credential}") 
	private boolean corsAllowCredentials;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
	        .allowedOrigins("http://localhost:3000")
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
	        .allowCredentials(true);
	}
	
}
