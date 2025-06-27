package com.example.service;

public interface PaymentService {
    public String paymentInfo_OK(Integer id);
    public String paymentInfo_Timeout(Integer id);

    String paymentCircuitBreaker(Integer id);
}
