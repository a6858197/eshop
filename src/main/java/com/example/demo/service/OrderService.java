package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.model.Cart;
import java.util.List;

public interface OrderService {

	// ✅ 全部結帳（原本）
	Long createOrder(User user, Cart cart, String paymentMethod, String invoiceType, String address);

	// ✅ 部分結帳（只結帳被勾選項目）
	Long createOrderPartially(User user, Long[] selectedItemIds, String paymentMethod, String invoiceType,
			String address);

	// 前台 / 後台：依訂單 ID 查詢訂單
	Order findById(Long id);

	// 前台：查詢會員自己的訂單
	List<Order> findByUserId(Long userId);

	// 後台：查全部訂單
	List<Order> findAll();

	// 更新訂單狀態（例：待付款 → 已付款 → 已出貨）
	void updateStatus(Long orderId, String status);

	// 刪除訂單（含明細）
	void delete(Long orderId);
}
