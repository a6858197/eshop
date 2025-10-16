package com.example.demo.service;

import com.example.demo.model.Product;
import java.util.List;

public interface ProductService {

	// 取得所有商品
	List<Product> getAllProducts();

	// 根據 ID 查詢單一商品
	Product getProductById(Long id);

	// 新增商品
	void saveProduct(Product product);

	// 更新商品資料
	void updateProduct(Long id, Product updatedProduct);

	// 刪除商品
	void deleteProduct(Long id);
}
