package com.example.demo.service.impl;

import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.OrderItemDAO;
import com.example.demo.dao.CartDAO;
import com.example.demo.model.Order;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional // ✅ 保證 Hibernate Session 在 createOrder 過程中不會關閉
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private OrderItemDAO orderItemDAO;

	@Autowired
	private CartDAO cartDAO;

	// ✅ 建立訂單
	@Override
	public Long createOrder(User user, Cart cart, String paymentMethod, String invoiceType, String address) {

		// ✅ 重新載入含 items 的購物車 (避免 LazyInitializationException)
		cart = cartDAO.findByMemberId(user.getId());

		if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
			throw new RuntimeException("❌ 購物車是空的，無法建立訂單");
		}

		// ✅ 建立訂單主檔
		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(new Date());
		order.setPaymentMethod(paymentMethod);
		order.setInvoiceType(invoiceType);
		order.setAddress(address);
		order.setStatus("待付款");

		// ✅ 初始化訂單明細列表（重要！避免 NPE）
		order.setItems(new ArrayList<>());

		double total = 0.0;

		// ✅ 逐一轉成訂單明細
		for (CartItem item : cart.getItems()) {

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(item.getPriceSnapshot());
			orderItem.setSubtotal(item.getSubtotal());

			order.getItems().add(orderItem); // ✅ 加入明細
			total += item.getSubtotal();
		}

		order.setTotal(total);

		// ✅ 儲存訂單與明細
		orderDAO.save(order);

		// ✅ 清空購物車
		cart.getItems().clear();
		cartDAO.save(cart);

		return order.getId();
	}

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
		orderItemDAO.deleteByOrderId(orderId);
		Order order = orderDAO.findById(orderId);
		if (order != null) {
			orderDAO.save(order);
		}
	}
}
