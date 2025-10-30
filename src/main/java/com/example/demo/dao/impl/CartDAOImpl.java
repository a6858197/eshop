package com.example.demo.dao.impl;

import com.example.demo.dao.CartDAO;
import com.example.demo.model.Cart;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Repository
public class CartDAOImpl implements CartDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	// ✅ 依 member_id 取得購物車
	@Override
	public Cart findByMemberId(Long memberId) {
		String hql = "FROM Cart c WHERE c.member.id = :memberId";
		Query<Cart> query = getSession().createQuery(hql, Cart.class);
		query.setParameter("memberId", memberId);
		return query.uniqueResult();
	}

	// ✅ 建立購物車
	@Override
	public void createCart(Cart cart) {
		getSession().save(cart);
	}

	// ✅ 更新購物車（Hibernate 會追蹤變更）
	@Override
	public void updateCart(Cart cart) {
		getSession().update(cart);
	}

	// ✅ 清空購物車（刪除所有 CartItem）
	@Override
	public void clearCart(Long cartId) {
		getSession().createQuery("DELETE FROM CartItem WHERE cart.id = :cartId").setParameter("cartId", cartId)
				.executeUpdate();
	}
}
