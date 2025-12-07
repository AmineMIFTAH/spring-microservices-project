package com.cloud.ProductService.controller;

import com.cloud.ProductService.entity.Product;
import com.cloud.ProductService.model.ProductRequest;
import com.cloud.ProductService.model.ProductResponse;
import com.cloud.ProductService.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    public final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {

        long productId = productService.addProduct(productRequest);

        return new ResponseEntity<>(productId, HttpStatus.CREATED);


    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long productId) {
        ProductResponse product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity) {
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
