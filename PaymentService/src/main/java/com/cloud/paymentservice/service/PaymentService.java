package com.cloud.paymentservice.service;

import com.cloud.paymentservice.model.PaymentRequest;

public interface PaymentService { long doPayment(PaymentRequest paymentRequest);
}
