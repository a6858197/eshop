package com.example.demo.dao.impl;

import com.example.demo.dao.CartDAO;
import com.example.demo.model.Cart;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class CartDAOImpl implements CartDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/** ✅ 使用 fetch join 強制載入 items 避免 LazyInitializationException */
	@Override
	public Cart findByMemberId(Long memberId) {
		return getSession()
				.createQuery("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.member.id = :memberId", Cart.class)
				.setParameter("memberId", memberId).uniqueResult();
	}

	@Override
	public void save(Cart cart) {
		getSession().saveOrUpdate(cart);
	}

	/** ✅ 清空購物車內容 (刪除 CartItem，而不是刪 Cart) */
	@Override
	public void clearCart(Long cartId) {
		getSession().createQuery("DELETE FROM CartItem WHERE cart.id = :cartId").setParameter("cartId", cartId)
				.executeUpdate();
	}
}
