package com.star.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * @author liuxing
 * @description
 * @create: 2022-10-10 21:04
 */

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    @Value("${nature.restApi.enabled}")
    private Boolean enable;

    /**
     * 项目应用名
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 项目版本信息
     */
    @Value("${spring.application.version}")
    private String applicationVersion;

    /**
     * 项目描述信息
     */
    @Value("${spring.application.description}")
    private String applicationDescription;

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                //配置需要扫描的controller位置
                .apis(RequestHandlerSelectors.basePackage("com.star"))
                //配置路径
                .paths(PathSelectors.any())
                //构建
                .build()
                //将servlet路径映射（如果有）添加到apis基本路径
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                // 定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(enable)
                //文档信息
                .apiInfo(apiInfo())
                // 支持的通讯协议集合
                .protocols(new LinkedHashSet<>(Arrays.asList("https", "http")))
                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(singletonList(apiKey()))
                // 授权信息全局应用
                .securityContexts(singletonList(securityContext())
                );
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                //标题
                .title(applicationName + " RESTful API 文档")
                //版本
                .version("应用版本: " + applicationVersion + ", " +
                        "Spring Boot版本: " + SpringBootVersion.getVersion())
                //描述
                .description(applicationDescription)
                //联系人信息
                .contact(new Contact("liuxing",//名字
                        "https://www.baidu.com",//网址
                        "13996864257@163.com"))//邮箱
                //License
                .license("Apache 2.0")
                //License 网址
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .build();
        return apiInfo;
    }

    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(
                new SecurityReference("mykey", authorizationScopes));
    }
}

