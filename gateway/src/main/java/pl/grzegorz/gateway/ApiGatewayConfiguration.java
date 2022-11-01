package pl.grzegorz.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p.path("/api/motorcycles/**")
                        .uri("lb://motorcycle-service"))
                .route(p -> p.path("/api/motorcycle-classes/**")
                        .uri("lb://motorcycle-service"))
                .route(p -> p.path("/api/bikers/**")
                        .uri("lb://biker-service"))
                .build();
    }
}