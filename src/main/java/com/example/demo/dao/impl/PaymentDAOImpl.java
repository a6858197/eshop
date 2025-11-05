package com.example.demo.dao.impl;

import com.example.demo.dao.PaymentDAO;
import com.example.demo.model.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PaymentDAOImpl implements PaymentDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Payment payment) {
		entityManager.persist(payment);
	}

	@Override
	public List<Payment> findByOrderId(Long orderId) {
		return entityManager.createQuery("SELECT p FROM Payment p WHERE p.order.id = :orderId", Payment.class)
				.setParameter("orderId", orderId).getResultList();
	}
}
