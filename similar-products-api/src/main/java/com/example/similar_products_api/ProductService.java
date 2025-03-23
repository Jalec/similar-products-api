package com.example.similar_products_api;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Arrays;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final WebClient webClient;

    public ProductService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:3001").build();
    }

    public Mono<List<String>> getSimilarProductIds(String productId) {
        logger.info("Fetching similar product IDs for productId: {}", productId);
        return webClient.get()
                .uri("/product/{productId}/similarids", productId)
                .retrieve()
                .bodyToMono(String[].class)  
                .map(Arrays::asList)         
                .doOnNext(ids -> logger.info("Received similar product IDs: {}", ids));
    }

    public Mono<ProductDetail> getProductDetail(String productId) {
        logger.info("Fetching product details for productId: {}", productId);
        return webClient.get()
                .uri("/product/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductDetail.class)
                .onErrorResume(error -> {
                    logger.error("Error fetching product details for productId: {}", productId, error);
                    return Mono.just(new ProductDetail(productId, "Unknown Product", 0, false));
                });
    }

    public Flux<ProductDetail> getSimilarProductDetails(String productId) {
        return getSimilarProductIds(productId)
            .flatMapMany(Flux::fromIterable)
            .flatMap(this::getProductDetail);
    }
}