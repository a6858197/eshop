package com.example.demo.dao;

import com.example.demo.model.Cart;

public interface CartDAO {

	/** 依會員 ID 取得購物車 (含 CartItems，使用 fetch join) */
	Cart findByMemberId(Long memberId);

	/** 新增或更新購物車 */
	void save(Cart cart);

	/** ✅ 清空購物車內所有項目 */
	void clearCart(Long cartId);

	/** 🔁 相容舊程式用法 → 導向 save() */
	@Deprecated
	default void createCart(Cart cart) {
		save(cart);
	}

	/** 🔁 相容舊程式用法 → 導向 save() */
	@Deprecated
	default void updateCart(Cart cart) {
		save(cart);
	}
}
