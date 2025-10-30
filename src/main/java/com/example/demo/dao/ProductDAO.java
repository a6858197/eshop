package com.example.demo.dao;

import com.example.demo.model.Product;
import java.util.List;

public interface ProductDAO {
	
	// ✅ 查全部商品
    List<Product> findAll();
    
    // ✅ 新增分類查詢
    List<Product> findByCategory(String category);
    
    // ✅ 模糊搜尋商品
    List<Product> searchByKeyword(String keyword);

    // ✅ 查單一商品
    Product findById(Long id);

    // ✅ 新增商品
    void save(Product product);

    // ✅ 更新商品
    void update(Long id, Product product);

    // ✅ 刪除商品
    void delete(Long id);
}
