package com.aaron.api_gateway.config;

import com.aaron.api_gateway.filter.JwtValidationGatewayFilterFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${gateway.services.patient}")
    private String patientServiceUri;

    @Value("${gateway.services.auth}")
    private String authServiceUri;

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder, JwtValidationGatewayFilterFactory jwtValidate) {
        return builder.routes()
                .route("patient-service-route", r -> r
                        .path("/api/patients/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .filter(jwtValidate.apply(new JwtValidationGatewayFilterFactory.Config())))
                        .uri(patientServiceUri))

                .route("auth-service-route", r -> r
                        .path("/auth/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(authServiceUri))

                .route("api-docs-auth-service", r -> r
                        .path("/api-docs/auth/**")
                        .filters(f -> f.rewritePath("/api-docs/auth", "/v3/api-docs"))
                        .uri(authServiceUri))

                .route("api-docs-patient-service", r -> r
                        .path("/api-docs/patients/**")
                        .filters(f -> f.rewritePath("/api-docs/patients", "/v3/api-docs"))
                        .uri(patientServiceUri))

                .build();
    }
}