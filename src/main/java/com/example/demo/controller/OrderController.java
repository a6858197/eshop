package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private PaymentService paymentService;

	/** ✅ 我的訂單列表 */
	@GetMapping
	public String listMyOrders(HttpSession session, Model model) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		model.addAttribute("orders", orderService.findByUserId(user.getId()));
		return "order-list";
	}

	/** ✅ 查看訂單明細 */
	@GetMapping("/{id}")
	public String viewOrderDetail(@PathVariable Long id, HttpSession session, Model model) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		model.addAttribute("order", orderService.findById(id));
		return "order-detail";
	}

	/** ✅ 更新訂單狀態（後台 or 用戶操作） */
	@PostMapping("/{id}/update-status")
	public String updateOrderStatus(@PathVariable Long id, @RequestParam String status, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		orderService.updateStatus(id, status);
		return "redirect:/orders";
	}

	/** ✅ 付款 */
	@PostMapping("/{id}/pay")
	public String payOrder(@PathVariable Long id, @RequestParam(defaultValue = "ATM") String method,
			@RequestParam(defaultValue = "Bank") String provider, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		var order = orderService.findById(id);
		paymentService.pay(order, method, provider);

		return "redirect:/orders/" + id;
	}

	/** ✅ 結帳頁 */
	@GetMapping("/checkout")
	public String checkoutPage(HttpSession session, Model model) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		Cart cart = cartService.getCartByMember(user);
		if (cart == null || cart.getItems().isEmpty())
			return "redirect:/cart";

		model.addAttribute("cart", cart);
		return "checkout";
	}

	/** ✅ 提交訂單（下單） */
	@PostMapping("/submit")
	public String submitOrder(HttpSession session, Model model, @RequestParam String receiverName,
			@RequestParam String receiverPhone, @RequestParam String receiverAddress,
			@RequestParam(defaultValue = "ATM") String paymentMethod,
			@RequestParam(defaultValue = "二聯式") String invoiceType) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		Cart cart = cartService.getCartByMember(user);
		if (cart == null)
			return "redirect:/cart";

		Long orderId = orderService.createOrder(user, cart, paymentMethod, invoiceType, receiverAddress);

		model.addAttribute("orderId", orderId);
		return "order-success";
	}

	/** ✅ 刪除訂單 */
	@GetMapping("/delete/{id}")
	public String deleteOrder(@PathVariable Long id, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null)
			return "redirect:/login";

		orderService.delete(id);
		return "redirect:/orders";
	}
}
