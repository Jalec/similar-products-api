package com.example.similar_products_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<Flux<ProductDetail>> getSimilarProducts(@PathVariable String productId) {
        Flux<ProductDetail> similarProducts = productService.getSimilarProductDetails(productId);
        return ResponseEntity.ok(similarProducts);
    }
}
