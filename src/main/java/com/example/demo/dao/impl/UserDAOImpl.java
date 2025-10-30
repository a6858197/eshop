package com.example.demo.dao.impl;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<User> findAll() {
		return getCurrentSession().createQuery("FROM User", User.class).list();
	}

	@Override
	public User findById(Long id) {
		return getCurrentSession().get(User.class, id);
	}

	@Override
	public void save(User user) {
		getCurrentSession().saveOrUpdate(user);
	}

	@Override
	public void delete(Long id) {
		User user = getCurrentSession().get(User.class, id);
		if (user != null) {
			getCurrentSession().delete(user);
		}
	}

	@Override
	public User findByNameAndPassword(String name, String password) {
		String hql = "FROM User WHERE name = :name AND password = :password";
		return getCurrentSession().createQuery(hql, User.class).setParameter("name", name)
				.setParameter("password", password).uniqueResult();
	}

	// ✅ 新增：依 Email 查詢（忘記密碼會用）
	@Override
	public User findByEmail(String email) {
		String hql = "FROM User WHERE email = :email";
		return getCurrentSession().createQuery(hql, User.class).setParameter("email", email).uniqueResult();
	}

}
