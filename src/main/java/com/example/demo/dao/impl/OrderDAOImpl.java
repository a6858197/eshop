package com.example.demo.dao.impl;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Order order) {
		if (order.getId() == null) {
			entityManager.persist(order); // 新增
		} else {
			entityManager.merge(order); // 更新
		}
	}

	@Override
	public Order findById(Long id) {
		return entityManager.find(Order.class, id);
	}

	@Override
	public List<Order> findByUserId(Long userId) {
		return entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId", Order.class)
				.setParameter("userId", userId).getResultList();
	}

	@Override
	public List<Order> findAll() {
		return entityManager.createQuery("SELECT o FROM Order o ORDER BY o.orderDate DESC", Order.class)
				.getResultList();
	}
}
