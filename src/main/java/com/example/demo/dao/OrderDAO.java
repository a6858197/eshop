package com.example.demo.dao;

import com.example.demo.model.Order;
import java.util.List;

public interface OrderDAO {

	void save(Order order);

	Order findById(Long id);

	List<Order> findByUserId(Long userid);

	List<Order> findAll();
}
