package com.example.demo.dao;

import com.example.demo.model.OrderItem;
import java.util.List;

public interface OrderItemDAO {

    void save(OrderItem orderItem);

    OrderItem findById(Long id);

    List<OrderItem> findByOrderId(Long orderId); 

    void deleteByOrderId(Long orderId);           
}
