package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.OrderItem;

public interface OrderItemDAO {
    List<OrderItem> findByOrderId(Long orderId);
    void save(OrderItem item);
    void deleteByOrderId(Long orderId);
}
