package com.example.demo.dao.impl;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Product> findAll() {
		return getSession().createQuery("FROM Product", Product.class).list();
	}

	// ✅ 新增分類查詢實作
	@Override
	public List<Product> findByCategory(String category) {
		return getSession().createQuery("FROM Product WHERE category = :category", Product.class)
				.setParameter("category", category).list();
	}

	@Override
	public List<Product> searchByKeyword(String keyword) {
		return getSession().createQuery("FROM Product WHERE name LIKE :keyword", Product.class)
				.setParameter("keyword", "%" + keyword + "%").list();
	}

	@Override
	public Product findById(Long id) {
		return getSession().get(Product.class, id);
	}

	@Override
	public void save(Product product) {
		getSession().saveOrUpdate(product);
	}

	@Override
	public void update(Long id, Product product) {
		getSession().update(product);
	}

	@Override
	public void delete(Long id) {
		Product product = findById(id);
		if (product != null)
			getSession().delete(product);
	}
}
