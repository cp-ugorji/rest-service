/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stealth.rest.webservices;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Chux
 */
//Configuration
@Configuration
//enable swagger
@EnableSwagger2
public class SwaggerConfig {
    public static final Contact DEFAULT_CONTACT = new Contact("Chukwudi Ugorji", "www.stealthtechnologyz.com", "info@stealthtechnologyz.com");
    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("User API", "This API is for CRUD operation on our User Platform", "1.0", 
            "terms and condition applies", DEFAULT_CONTACT.toString(), "Apache 2.0", "http://www.apache.org/license/LICENSE-2.0");
    
    //Bean
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO);
    }
}
