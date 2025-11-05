package com.example.demo.dao.impl;

import com.example.demo.dao.OrderItemDAO;
import com.example.demo.model.OrderItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class OrderItemDAOImpl implements OrderItemDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(OrderItem orderItem) {
		if (orderItem.getId() == null) {
			entityManager.persist(orderItem);
		} else {
			entityManager.merge(orderItem);
		}
	}

	@Override
	public OrderItem findById(Long id) {
		return entityManager.find(OrderItem.class, id);
	}

	@Override
	public List<OrderItem> findByOrderId(Long orderId) {
		return entityManager.createQuery("SELECT i FROM OrderItem i WHERE i.order.id = :orderId", OrderItem.class)
				.setParameter("orderId", orderId).getResultList();
	}

	@Override
	public void deleteByOrderId(Long orderId) {
		entityManager.createQuery("DELETE FROM OrderItem i WHERE i.order.id = :orderId")
				.setParameter("orderId", orderId).executeUpdate();
	}
}
