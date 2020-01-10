package com.unict.riganozito.apigateway.router;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(EmbeddedDataSourceConfiguration.class)
public class GatewayRoutes {

    @Value(value = "${videoservice.videomanagementservice}")
    private String url;

    @Value(value = "${videoservice.storage}")
    private String storage;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes().route(
                r -> r.path("/vms/**").filters(f -> f.rewritePath("/vms(/?|)(.*)", "/$2")).uri(url))

                .route(r -> r.path("/videofiles").uri(storage)).build();
    }
}
