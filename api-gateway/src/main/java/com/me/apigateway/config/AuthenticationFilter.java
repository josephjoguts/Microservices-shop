package com.me.apigateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.apigateway.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.xml.transform.Result;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtService jwtUtil;

    private static ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey("AUTH-TOKEN")) {
                    ServerHttpResponse response = exchange.getResponse();
                    try {
                        return getVoidMono(response, "no AUTH-TOKEN");
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
                String authHeader = exchange.getRequest().getHeaders().get("AUTH-TOKEN").get(0);
                try {
                    jwtUtil.validate(authHeader);
                    String userName = jwtUtil.extractUsername(authHeader);
                    ServerHttpRequest user = exchange.getRequest()
                            .mutate()
                            .header("USER", userName).build();
                    return chain.filter(exchange.mutate().request(user).build());
                } catch (Exception e) {
                    ServerHttpResponse response = exchange.getResponse();
                    try {
                        return getVoidMono(response,  "Forbidden");
                    } catch (JsonProcessingException jsonProcessingException) {
                        throw new RuntimeException(jsonProcessingException);
                    }
                }
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> getVoidMono(ServerHttpResponse response, String msg) throws JsonProcessingException {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.FORBIDDEN);
        byte[] bits = objectMapper.writeValueAsBytes(String.format("{result: %s}", msg));
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {

    }
}
