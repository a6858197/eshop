package com.example.demo.dao;

import com.example.demo.model.CartItem;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;

import java.util.List;

public interface CartItemDAO {

	CartItem findByCartIdAndProductId(Long cartId, Long productId);

	CartItem findById(Long id);
	
	CartItem findByCartAndProduct(Cart cart, Product product);
	
	List<CartItem> findByCart(Cart cart);
	
	void save(CartItem item);

	void update(CartItem item);

	void delete(Long id);

	/** ✅ 新增：刪除整個購物車項目 */
    void deleteByCartId(Long cartId);	
}
