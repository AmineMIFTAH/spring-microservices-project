package com.cloud.orderservice.service;

import com.cloud.orderservice.entity.Order;
import com.cloud.orderservice.external.client.ProductService;
import com.cloud.orderservice.model.OrderRequest;
import com.cloud.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }


    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //Order Entity -> Save the data with Status Order Created
        //Product Service - Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success-> COMPLETE, Else
        //CANCELLED

        log.info("Placing Order Request: {}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating Order with Status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        orderRepository.save(order);

        log.info("Order Places successfully with Order Id: {}", order.getId());
        return order.getId();
    }
}

