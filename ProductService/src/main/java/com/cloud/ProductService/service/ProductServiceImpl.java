package com.cloud.ProductService.service;

import com.cloud.ProductService.entity.Product;
import com.cloud.ProductService.exception.ProductServiceCustomException;
import com.cloud.ProductService.model.ProductRequest;
import com.cloud.ProductService.model.ProductResponse;
import com.cloud.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product.. ");

        Product product =
                Product.builder()
                        .productName(productRequest.getName())
                        .quantity(productRequest.getQuantity())
                        .price(productRequest.getPrice())
                        .build();
        productRepository.save(product);

        log.info("Product added successfully.");

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("Get the product for productId: {}", productId);

        Product product
                = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ProductServiceCustomException("Product with given id not found","PRODUCT_NOT_FOUND"));

        ProductResponse productResponse
                = new ProductResponse();

        copyProperties(product, productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(Long productId, long quantity) {
        log.info("Reduce quantity for the product with given productId: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException(
                        "Product with given id not found","PRODUCT_NOT_FOUND"
                ));
        if(product.getQuantity()<quantity){
            throw new ProductServiceCustomException(
                    "Product does not have sufficient Quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }
        product.setQuantity(product.getQuantity()-quantity);
        log.info("Product updated successfully.");

        productRepository.save(product);
    }

}
