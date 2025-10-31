package com.example.demo.service.impl;

import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.OrderItemDAO;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private OrderItemDAO orderItemDAO;

	@Override
	public List<Order> findAll() {
		return orderDAO.findAll();
	}

	@Override
	public Order findById(Long id) {
		return orderDAO.findById(id);
	}

	@Override
	public List<Order> findByUserId(Long userId) {
		return orderDAO.findByUserId(userId);
	}

	@Override
	public void updateStatus(Long orderId, String status) {
		Order order = orderDAO.findById(orderId);
		if (order != null) {
			order.setStatus(status);
			orderDAO.save(order);
		}
	}

	@Override
	public void delete(Long orderId) {
		// 先刪除明細，再刪訂單
		orderItemDAO.deleteByOrderId(orderId);
		Order order = orderDAO.findById(orderId);
		if (order != null) {
			// orderDAO.save(order) 可改為 remove 或 delete 依你 DAO 實作
			orderDAO.save(order);
		}
	}
}
