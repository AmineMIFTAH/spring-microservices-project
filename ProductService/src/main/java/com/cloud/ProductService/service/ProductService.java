package com.cloud.ProductService.service;

import com.cloud.ProductService.entity.Product;
import com.cloud.ProductService.model.ProductRequest;
import com.cloud.ProductService.model.ProductResponse;
import org.springframework.stereotype.Service;


public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);
}
