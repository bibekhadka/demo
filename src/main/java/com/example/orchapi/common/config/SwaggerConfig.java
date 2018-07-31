package com.example.orchapi.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!test")
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket xtrnetSalesPromotion() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.forCodeGeneration(true)
				.ignoredParameterTypes(Element.class, Node.class)
				.useDefaultResponseMessages(false);
	}

	@Bean
	UiConfiguration uiConfig() {
            
		return UiConfigurationBuilder.builder()
                    .validatorUrl("validatorUrl")
                    .docExpansion(DocExpansion.LIST)
                    .tagsSorter(TagsSorter.ALPHA)
                    .defaultModelRendering(ModelRendering.MODEL)
                    .displayRequestDuration(Boolean.TRUE)
                    .build();
	}
}
