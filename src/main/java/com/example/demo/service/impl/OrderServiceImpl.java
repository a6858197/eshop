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
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private OrderItemDAO orderItemDAO;

	@Autowired
	private CartDAO cartDAO;

	/** ✅ 全部結帳 */
	@Override
	public Long createOrder(User user, Cart cart, String paymentMethod, String invoiceType, String address) {

		cart = cartDAO.findByMemberId(user.getId());

		if (cart == null || cart.getItems().isEmpty())
			throw new RuntimeException("❌ 購物車是空的，無法建立訂單");

		return createOrderFromItems(user, cart.getItems(), cart, paymentMethod, invoiceType, address, true);
	}

	/** ✅ 部分結帳：只轉換勾選的商品 */
	@Override
	public Long createOrderPartially(User user, Long[] selectedItemIds, String paymentMethod, String invoiceType,
			String address) {

		Cart cart = cartDAO.findByMemberId(user.getId());
		if (cart == null || cart.getItems().isEmpty())
			throw new RuntimeException("❌ 購物車是空的");

		List<CartItem> checkoutItems = new ArrayList<>();

		for (CartItem item : cart.getItems()) {
			for (Long sid : selectedItemIds) {
				if (item.getId().equals(sid)) {
					checkoutItems.add(item);
				}
			}
		}

		return createOrderFromItems(user, checkoutItems, cart, paymentMethod, invoiceType, address, false);
	}

	/** ✅ 共用建立訂單邏輯 */
	private Long createOrderFromItems(User user, List<CartItem> itemsToOrder, Cart cart, String paymentMethod,
			String invoiceType, String address, boolean clearWholeCart) {

		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(new Date());
		order.setPaymentMethod(paymentMethod);
		order.setInvoiceType(invoiceType);
		order.setAddress(address);
		order.setStatus("待付款");

		order.setItems(new ArrayList<>());
		double total = 0;

		for (CartItem item : itemsToOrder) {
			OrderItem oi = new OrderItem();
			oi.setOrder(order);
			oi.setProduct(item.getProduct());
			oi.setQuantity(item.getQuantity());
			oi.setPrice(item.getPriceSnapshot());
			oi.setSubtotal(item.getSubtotal());

			order.getItems().add(oi);
			total += item.getSubtotal();
		}

		order.setTotal(total);
		orderDAO.save(order);

		// ✅ 清除購物車中已結帳商品
		if (clearWholeCart) {
			cart.getItems().clear();
		} else {
			cart.getItems().removeAll(itemsToOrder);
		}

		cartDAO.save(cart);
		return order.getId();
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
	public List<Order> findAll() {
		return orderDAO.findAll();
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
			orderDAO.delete(order);
		}
	}
}
