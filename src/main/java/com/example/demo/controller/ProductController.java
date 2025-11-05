package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	// ✅ 商品列表 + 搜尋 + 分類 + 顯示登入狀態
	@GetMapping
	public String listProducts(@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "keyword", required = false) String keyword, Model model, HttpSession session) {

		List<Product> products;

		if (keyword != null && !keyword.trim().isEmpty()) {
			products = productService.searchProducts(keyword); // ✅ 搜尋商品
		} else if (category != null && !category.trim().isEmpty()) {
			products = productService.getProductsByCategory(category); // ✅ 分類查詢
		} else {	
			products = productService.getAllProducts(); // ✅ 全部商品
		}

		model.addAttribute("products", products);

		// ✅ 登入顯示使用者名稱
		User user = (User) session.getAttribute("loggedInUser");
		model.addAttribute("loggedInUser", user);

		return "product-list"; // 對應 /WEB-INF/views/products-list.html
	}

	// ✅ 商品詳細
	@GetMapping("/{id}")
	public String showProductDetail(@PathVariable("id") Long id, Model model, HttpSession session) {

		Product product = productService.getProductById(id);
		model.addAttribute("product", product);

		User user = (User) session.getAttribute("loggedInUser");
		model.addAttribute("loggedInUser", user);

		return "product-detail"; // 對應 /WEB-INF/views/product-detail.html
	}
}

/** 顯示新增商品表單 */
/*
 * @GetMapping("/add") public String showAddForm(Model model) {
 * model.addAttribute("product", new Product()); return "add-product"; // 對應
 * /WEB-INF/views/add-product.html }
 */

/** 顯示編輯商品表單 */
/*
 * @GetMapping("/edit/{id}") public String showEditForm(@PathVariable("id") Long
 * id, Model model) { Product product = productService.getProductById(id);
 * model.addAttribute("product", product); return "edit-product"; // 對應
 * /WEB-INF/views/edit-product.html }
 */

/** 更新商品資料 */
/*
 * @PostMapping("/update/{id}") public String updateProduct(@PathVariable("id")
 * Long id, @ModelAttribute("product") Product product) {
 * productService.updateProduct(id, product); return "redirect:/products"; }
 */

/** 新增商品 */
/*
 * @PostMapping("/save") public String saveProduct(@ModelAttribute Product
 * product) { productService.saveProduct(product); return "redirect:/products";
 * }
 */

/** 刪除商品 */
/*
 * @GetMapping("/delete/{id}") public String deleteProduct(@PathVariable("id")
 * Long id) { productService.deleteProduct(id); return "redirect:/products"; }
 */
