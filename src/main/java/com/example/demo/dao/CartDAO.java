package com.example.demo.dao;

import com.example.demo.model.Cart;

public interface CartDAO {

	// 依會員查詢購物車
	Cart findByMemberId(Long memberId);

	// 建立購物車（會員第一次加入時）
	void createCart(Cart cart);

	// 更新購物車內容（新增、修改數量後）
	void updateCart(Cart cart);

	// 清空購物車（結帳使用）
	void clearCart(Long cartId);
}
