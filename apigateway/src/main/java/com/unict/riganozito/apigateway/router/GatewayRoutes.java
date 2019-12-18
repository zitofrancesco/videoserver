package com.unict.riganozito.apigateway.router;

import com.unict.riganozito.apigateway.service.ApiGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayRoutes {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        System.out.println("Request arrived");
        return routeLocatorBuilder.routes()
                .route(r ->
                        r.path("/vms/**")
                            .filters(f -> f
                                    .rewritePath("/vms/(?.*)", "/${remains}")
                            )
                            .uri("http:/localhost:9090"))

                .route(r ->
                        r.path("/videofiles")
                                .uri("file:///var/videosfiles"))
                .build();
    }
}