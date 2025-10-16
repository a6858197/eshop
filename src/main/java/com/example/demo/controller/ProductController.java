package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService ProductService;

	/** 顯示所有商品列表 */
	@GetMapping
	public String listProducts(Model model) {
		model.addAttribute("products", ProductService.getAllProducts());
		return "Product-list"; // 對應 /WEB-INF/views/products.html
	}

	// 🔹 顯示單一商品詳細資訊
	@GetMapping("/{id}")
	public String showProductDetail(@PathVariable("id") Long id, Model model) {
		Product product = ProductService.getProductById(id);
		model.addAttribute("product", product);
		return "Product-detail"; // 對應 /WEB-INF/views/product-detail.html
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
}
