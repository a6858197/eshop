package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CartService cartService;

	/** ✅ 取得 Session 購物車 */
	private Cart getSessionCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

	/** ✅ 加入購物車 */
	@PostMapping("/add/{productId}")
	public String addToCart(@PathVariable Long productId, @RequestParam(defaultValue = "1") int quantity,
			HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		Product product = productService.getProductById(productId);

		if (user == null) {
			Cart sessionCart = getSessionCart(session);
			sessionCart.addProduct(product, quantity);
			session.setAttribute("msg", "✅ 已加入購物車！");
			return "redirect:/products";
		}

		cartService.addToMemberCart(user, product, quantity);
		session.setAttribute("msg", "✅ 已成功加入購物車！");
		return "redirect:/products";
	}

	/** ✅ 查看購物車 */
	@GetMapping
	public String viewCart(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedInUser");

		if (user == null) {
			model.addAttribute("cart", getSessionCart(session));
		} else {
			model.addAttribute("cart", cartService.getCartByMember(user));
		}

		// ✅ 顯示一次性訊息
		model.addAttribute("msg", session.getAttribute("msg"));
		session.removeAttribute("msg");

		return "cart";
	}

	/** ✅ 登入 → 更新數量 */
	@PostMapping("/update/{itemId}")
	public String updateDBItem(@PathVariable Long itemId, @RequestParam int quantity, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user != null) {
			cartService.updateQuantity(user, itemId, quantity);
		}
		return "redirect:/cart";
	}

	/** ✅ 未登入 → 更新 Session 購物車 */
	@PostMapping("/update")
	public String updateSessionItem(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {

		Cart cart = getSessionCart(session);
		cart.updateQuantity(productId, quantity);
		return "redirect:/cart";
	}

	/** ✅ 登入 → 移除 */
	@GetMapping("/remove/{itemId}")
	public String removeDBItem(@PathVariable Long itemId, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user != null) {
			cartService.removeItem(user, itemId);
		}
		return "redirect:/cart";
	}

	/** ✅ 未登入 → 移除 */
	@GetMapping("/remove-session/{productId}")
	public String removeSessionItem(@PathVariable Long productId, HttpSession session) {

		Cart cart = getSessionCart(session);
		cart.removeProduct(productId);
		return "redirect:/cart";
	}

	/** ✅ 勾選 → 部分結帳 */
	@PostMapping("/checkout")
	public String checkoutSelected(@RequestParam(value = "selectedItems", required = false) Long[] selectedItems,
			HttpSession session) {

		if (selectedItems == null || selectedItems.length == 0) {
			session.setAttribute("msg", "⚠️ 請至少勾選一項商品再結帳");
			return "redirect:/cart";
		}

		session.setAttribute("selectedItems", selectedItems);
		return "redirect:/orders/checkout";
	}
}
