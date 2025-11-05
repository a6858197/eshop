package com.example.demo.service;

import com.example.demo.model.Payment;
import com.example.demo.model.Order;

import java.util.List;

public interface PaymentService {
	void pay(Order order, String type, String provider);

	List<Payment> getPaymentsByOrder(Long orderId);
}
