package com.example.demo.dao;

import com.example.demo.model.Payment;
import java.util.List;

public interface PaymentDAO {
	void save(Payment payment);

	List<Payment> findByOrderId(Long orderId);
}
