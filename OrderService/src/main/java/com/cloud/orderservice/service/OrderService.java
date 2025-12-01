package com.cloud.orderservice.service;

import com.cloud.orderservice.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
