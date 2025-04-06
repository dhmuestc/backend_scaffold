package com.example.backend_scaffold.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 * <p>
 * 配置Swagger文档，方便API的测试和文档化
 * </p>
 *
 * @author example
 */
@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI配置
     * <p>
     * 配置API文档的基本信息
     * </p>
     *
     * @return OpenAPI配置
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend Scaffold API")
                        .description("Backend scaffold project with RBAC model and DDD architecture")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Example")
                                .email("example@example.com")
                                .url("https://example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .name("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("JWT认证，请在请求头中添加Bearer令牌")));
    }
}