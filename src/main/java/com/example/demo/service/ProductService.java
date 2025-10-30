package com.example.demo.service;

import com.example.demo.model.Product;
import java.util.List;

public interface ProductService {

	List<Product> getAllProducts();

	// ✅ 新增分類查詢方法
	List<Product> getProductsByCategory(String category);

	List<Product> searchProducts(String keyword);

	Product getProductById(Long id);

	void saveProduct(Product product);

	void updateProduct(Long id, Product product);

	void deleteProduct(Long id);

}
