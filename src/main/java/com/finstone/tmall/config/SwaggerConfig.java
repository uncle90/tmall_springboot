package com.finstone.tmall.config;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
//    //https://www.cnblogs.com/tdyang/p/12142064.html
//    @Bean
//    public Docket swaggerSpringMvcPlugin() {
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .build();
//    }

    /*
    private Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Value("${swagger.enable}")
    private boolean isSwaggerEnable;

    @Bean
    public Docket createRestApi() {
        logger.info("SwaggerConfig createRestApi 构建REST API");
        logger.info("Swagger启用标志:{}",isSwaggerEnable);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhui.cotrollers")) //Controller所在包(必须新建包)
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        logger.info("SwaggerConfig apiInfo API信息");
        return new ApiInfoBuilder()
                .title("java-demo构建api文档")  //标题
                .description("swagger description")  //描述
                .version("1.0")
                .build();
    }

     */

    private Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Value("${spring.swagger.enable}")
    private boolean isSwaggerEnable;

    @Bean
    public Docket createRestApi() {
        logger.info("SwaggerConfig createRestApi 构建REST API");
        logger.info("Swagger启用标志:{}",isSwaggerEnable);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.finstone.tmall.web")) //Controller所在包(必须新建包)
                .paths(PathSelectors.any())
                .build();
        /*
                 .build()
                 .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
         */
    }
    private ApiInfo apiInfo() {
        logger.info("SwaggerConfig apiInfo");
        return new ApiInfoBuilder()
                .title("仿天猫Springboot API")  //标题
                .description("")  //描述
                .version("1.0")
                .build();
    }

}
