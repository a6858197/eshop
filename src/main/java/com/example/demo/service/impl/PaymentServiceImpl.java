package com.example.demo.service.impl;

import com.example.demo.dao.PaymentDAO;
import com.example.demo.model.Order;
import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDAO paymentDAO;

	@Override
	public void pay(Order order, String type, String provider) {
		Payment p = new Payment();
		p.setOrder(order);
		p.setPaymentType(type);
		p.setPaidAmount(order.getTotal());
		p.setProvider(provider);
		paymentDAO.save(p);

		// 訂單狀態更新為「已付款」
		order.setStatus("已付款");
	}

	@Override
	public java.util.List<Payment> getPaymentsByOrder(Long orderId) {
		return paymentDAO.findByOrderId(orderId);
	}
}
