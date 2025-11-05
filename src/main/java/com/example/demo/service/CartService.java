package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;

public interface CartService {

	Cart getCartByMember(User member);

	// 未登入 → 將商品加入 Session cart
	Cart addToGuestCart(Cart sessionCart, Product product, int quantity);

	// 會員登入 → 將商品加入 DB cart
	void addToMemberCart(User member, Product product, int quantity);

	// ⭐ 登入時執行：合併 Session 購物車 → DB
	void mergeCart(User member, Cart sessionCart);

	void updateQuantity(User member, Long productId, int quantity);

	void removeItem(User member, Long productId);
	
	void save(Cart cart);
	
	/** ✅ 新增：清空購物車 */
    void clearCart(Long cartId);
}
