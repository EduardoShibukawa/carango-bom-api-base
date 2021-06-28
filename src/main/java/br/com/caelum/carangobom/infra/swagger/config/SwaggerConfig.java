package br.com.caelum.carangobom.infra.swagger.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket carangoBomApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.caelum.carangobom"))
				.paths(PathSelectors.ant("/**"))
				.build();
	}
	
	private SecurityContext securityContext() {
		return SecurityContext
				.builder()
				.securityReferences(defaultAuth())
				.build();
				
	}
	
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
			= new AuthorizationScope("global", "accessEverything");
		
		
		return List.of(
				new SecurityReference(
						"JWT", 
						List.of(authorizationScope)
							.toArray(new AuthorizationScope[1])));
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}
}
