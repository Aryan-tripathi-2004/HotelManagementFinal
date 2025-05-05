package com.example.apigatewayservice.filter;

import com.example.apigatewayservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    private final List<String> openApiEndpoints = List.of(

            "/swagger-ui/",
            "/v3/api-docs/",
            "/api/auth/"
    );

    private final Map<String, List<String>> routeRoleMap = Map.of(
            "/api/rooms/available", List.of("OWNER", "MANAGER", "RECEPTIONIST"),
            "/api/rooms", List.of("OWNER", "MANAGER"),
            "/api/reservations", List.of("OWNER", "RECEPTIONIST"),
            "/api/bills", List.of("OWNER", "RECEPTIONIST"),
            "/api/guests", List.of("OWNER", "RECEPTIONIST"),
            "/api/inventories", List.of("OWNER", "RECEPTIONIST"),
            "/api/reports/", List.of("OWNER")
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        if (isOpenApi(path)) {
            return chain.filter(exchange);
        }

        List<String> authHeaders = exchange.getRequest().getHeaders().getOrEmpty("Authorization");

        if (authHeaders.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeaders.get(0);

        try {
            jwtUtil.validateToken(token);

            String role = jwtUtil.extractRole(token);

            if (!isAuthorized(path, role)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean isOpenApi(String path) {
        return openApiEndpoints.stream().anyMatch(path::contains);
    }

    private boolean isAuthorized(String path, String role) {
        for (Map.Entry<String, List<String>> entry : routeRoleMap.entrySet()) {
            String route = entry.getKey();
            List<String> allowedRoles = entry.getValue();

            if (path.startsWith(route)) {
                return allowedRoles.contains(role.toUpperCase());
            }
        }
        return false; // if route not in map, allow access
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
