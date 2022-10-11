package com.star.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author liuxing
 * @description 配置多个host
 */
@Component
public class StarSwaggerHostResolver implements WebMvcOpenApiTransformationFilter {

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI swagger = context.getSpecification();
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("本地测试时使用");
        Server remoteServer = new Server();
        remoteServer.setUrl("http://线上:端口");
        remoteServer.setDescription("线上开发环境使用");
        swagger.setServers(Arrays.asList(localServer, remoteServer));
        return swagger;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.OAS_30.equals(documentationType);
    }
}

