package com.example.demo.service.impl;

import com.example.demo.dao.CartDAO;
import com.example.demo.dao.CartItemDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDAO cartDAO;

	@Autowired
	private CartItemDAO cartItemDAO;

	@Autowired
	private ProductDAO productDAO;

	@Override
	public Cart getCartByMember(User member) {
		return cartDAO.findByMemberId(member.getId());
	}

	// ✅ 未登入：使用 Session 購物車
	@Override
	public Cart addToGuestCart(Cart sessionCart, Product product, int quantity) {

		if (sessionCart == null) {
			sessionCart = new Cart();
		}

		if (sessionCart.getItems() == null) {
			sessionCart.setItems(new ArrayList<>());
		}

		for (CartItem item : sessionCart.getItems()) {
			if (item.getProduct().getId().equals(product.getId())) {
				item.setQuantity(item.getQuantity() + quantity);
				return sessionCart;
			}
		}

		CartItem newItem = new CartItem();
		newItem.setCart(sessionCart);
		newItem.setProduct(product);
		newItem.setQuantity(quantity);
		newItem.setPriceSnapshot(product.getPrice());

		sessionCart.getItems().add(newItem);
		return sessionCart;
	}

	// ✅ 已登入 → 寫入 DB
	@Override
	public void addToMemberCart(User member, Product product, int quantity) {

		Cart cart = getCartByMember(member);

		if (cart == null) {
			cart = new Cart();
			cart.setMember(member);
			cartDAO.save(cart);
		}

		CartItem item = cartItemDAO.findByCartIdAndProductId(cart.getId(), product.getId());

		if (item == null) {
			item = new CartItem();
			item.setCart(cart);
			item.setProduct(product);
			item.setQuantity(quantity);
			item.setPriceSnapshot(product.getPrice());
			cartItemDAO.save(item);
		} else {
			item.setQuantity(item.getQuantity() + quantity);
			cartItemDAO.update(item);
		}
	}

	// ✅ 登入時 Session → DB 合併
	@Override
	public void mergeCart(User member, Cart sessionCart) {
		if (sessionCart == null || sessionCart.getItems() == null)
			return;

		for (CartItem sessionItem : sessionCart.getItems()) {
			addToMemberCart(member, sessionItem.getProduct(), sessionItem.getQuantity());
		}
	}

	// ✅ 更新數量
	@Override
	public void updateQuantity(User user, Long itemId, int quantity) {
		CartItem item = cartItemDAO.findById(itemId);
		if (item != null && item.getCart().getMember().getId().equals(user.getId())) {
			item.setQuantity(quantity);
			cartItemDAO.update(item);
		}
	}

	// ✅ 修正：移除單一商品（用 itemId，不用 productId）
	@Override
	public void removeItem(User user, Long itemId) {
		CartItem item = cartItemDAO.findById(itemId);
		if (item != null && item.getCart().getMember().getId().equals(user.getId())) {
			cartItemDAO.delete(itemId);
		}
	}

	@Override
	public void save(Cart cart) {
		cartDAO.save(cart);
	}

	@Override
	public void clearCart(Long cartId) {
		cartItemDAO.deleteByCartId(cartId);
	}
}
