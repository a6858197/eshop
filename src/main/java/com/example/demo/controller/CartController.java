package com.example.demo.controller;

import com.example.demo.service.CartService;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
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

	/** ✅ 取得 Session 購物車（未登入用） */
	private Cart getSessionCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

	/** ✅ 加入購物車（依登入狀態處理） */
	@PostMapping("/add/{productId}")
	public String addToCart(@PathVariable Long productId, @RequestParam int quantity, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		Product product = productService.getProductById(productId);

		if (user == null) {
			// 未登入 → 使用 Session 購物車
			Cart cart = getSessionCart(session);
			cart.addProduct(product, quantity);
			session.setAttribute("msg", "✅ 已加入購物車！");
			return "redirect:/products";
		}

		// ✅ 已登入 → 寫入資料庫
		cartService.addToMemberCart(user, product, quantity);
		session.setAttribute("msg", "✅ 已成功加入購物車！");
		return "redirect:/products";
	}

	/** ✅ 查看購物車頁面 */
	@GetMapping
	public String viewCart(HttpSession session, Model model) {

		User user = (User) session.getAttribute("loggedInUser");

		if (user == null) {
			// 未登入 → 用 Session 購物車
			Cart cart = getSessionCart(session);
			model.addAttribute("cart", cart);
			return "cart";
		}

		// ✅ 已登入 → 用 DB 購物車
		Cart cart = cartService.getCartByMember(user);
		model.addAttribute("cart", cart);
		return "cart";
	}

	/** ✅ 更新購物車數量（限登入會員） */
	@PostMapping("/update/{itemId}")
	public String updateQuantity(@PathVariable Long itemId, @RequestParam int quantity, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		cartService.updateQuantity(user, itemId, quantity);
		return "redirect:/cart";
	}

	/** ✅ 移除商品（限登入會員） */
	@GetMapping("/remove/{itemId}")
	public String removeItem(@PathVariable Long itemId, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		cartService.removeItem(user, itemId);
		return "redirect:/cart";
	}

	/** ✅ 前往結帳 */
	@GetMapping("/checkout")
	public String checkoutPage(HttpSession session, Model model) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		Cart cart = cartService.getCartByMember(user);
		model.addAttribute("cart", cart);
		return "checkout";
	}
}
