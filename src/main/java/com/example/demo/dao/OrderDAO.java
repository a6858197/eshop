package com.example.demo.dao;

import com.example.demo.model.Order;
import java.util.List;

public interface OrderDAO {

	/** 新增 or 修改訂單 */
	void save(Order order);

	/** 依訂單 ID 查詢 */
	Order findById(Long id);

	/** 查詢使用者的全部訂單 */
	List<Order> findByUserId(Long userId);

	/** 查全部訂單（後台用） */
	List<Order> findAll();

	/** ✅ 新增：刪除訂單（這就是你缺的） */
	void delete(Order order);
}
