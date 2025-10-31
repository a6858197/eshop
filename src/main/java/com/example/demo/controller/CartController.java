package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller

public class CartController {

	@Autowired
	private ProductService productService;

	// ✅ 取得購物車（存放在 Session）
	private Cart getCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

	// ✅ 加入購物車
	@PostMapping("/add/{productId}")
	public String addToCart(@PathVariable Long productId, @RequestParam int quantity, HttpSession session) {

		Product product = productService.getProductById(productId);
		Cart cart = getCart(session);

		cart.addProduct(product, quantity);

		return "redirect:/products";
	}

	// ✅ 查看購物車頁面
	@GetMapping
	public String viewCart(Model model, HttpSession session) {
		Cart cart = getCart(session);
		model.addAttribute("cart", cart);
		return "cart"; // 對應 cart.html
	}

	// ✅ 前往結帳頁面
	@GetMapping("/checkout")
	public String checkout(HttpSession session, Model model) {
		Cart cart = getCart(session);
		model.addAttribute("cart", cart);
		return "checkout"; // 對應 checkout.html
	}

	// ✅ 更新購物車數量
	@PostMapping("/update/{productId}")
	public String updateQuantity(@PathVariable Long productId, @RequestParam int quantity, HttpSession session) {

		getCart(session).updateQuantity(productId, quantity);
		return "redirect:/cart";
	}

	// ✅ 移除商品
	@GetMapping("/remove/{productId}")
	public String removeItem(@PathVariable Long productId, HttpSession session) {
		getCart(session).removeProduct(productId);
		return "redirect:/cart";
	}
}
