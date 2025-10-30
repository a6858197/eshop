package com.example.demo.dao;

import com.example.demo.model.CartItem;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;

import java.util.List;

public interface CartItemDAO {

	CartItem findByCartIdAndProductId(Long cartId, Long productId);

	void save(CartItem cartItem);

	void update(CartItem cartItem);

	void delete(Long id);

	CartItem findById(Long id);

	List<CartItem> findByCart(Cart cart);

	CartItem findByCartAndProduct(Cart cart, Product product);
}
