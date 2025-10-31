package com.example.demo.service;

import com.example.demo.model.Order;
import java.util.List;

public interface OrderService {

	// 後台：查全部訂單
	List<Order> findAll();

	// 前台 / 後台：依訂單 ID 取得訂單
	Order findById(Long id);

	// 前台：查詢會員自己的訂單
	List<Order> findByUserId(Long userId);

	// 更新訂單狀態（例：待付款 → 已付款 → 已出貨）
	void updateStatus(Long orderId, String status);

	// 後台刪除訂單
	void delete(Long orderId);
}
