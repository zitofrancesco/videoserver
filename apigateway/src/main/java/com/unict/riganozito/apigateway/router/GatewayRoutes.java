package com.unict.riganozito.apigateway.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayRoutes {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(r ->
                        r.path("/vms/**")
                            .filters(f -> f
                                    .rewritePath("/vms/?<segment>.*", "/${segment}")
                            )
                            .uri("http://localhost:8080"))

                .route(r ->
                        r.path("/videofiles")
                                .uri("file:///var/videosfiles"))
                .build();
    }
}
