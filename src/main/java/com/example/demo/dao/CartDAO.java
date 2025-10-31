package com.example.demo.dao;

import com.example.demo.model.Cart;

public interface CartDAO {

	Cart findByMemberId(Long memberId);

	/** 統一新增/更新 */
	void save(Cart cart);

	/** 舊方法：保留相容性，直接導向 save() */
	@Deprecated
	default void createCart(Cart cart) {
		save(cart);
	}

	/** 舊方法：保留相容性，直接導向 save() */
	@Deprecated
	default void updateCart(Cart cart) {
		save(cart);
	}

	void clearCart(Long cartId);
}
