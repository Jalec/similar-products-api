package com.example.similar_products_api;

import java.util.List;

public class SimilarProducts {
    private List<ProductDetail> products;

    // Constructor
    public SimilarProducts(List<ProductDetail> products) {
        this.products = products;
    }

    // Getter and Setter
    public List<ProductDetail> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetail> products) {
        this.products = products;
    }
}
