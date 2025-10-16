package com.example.demo.dao;

import com.example.demo.model.Product;
import java.util.List;

public interface ProductDAO {

    // 查詢所有商品
    List<Product> findAll();

    // 根據 ID 查詢單一商品
    Product findById(Long id);

    // 新增或更新商品
    void save(Product product);

    // 根據 ID 刪除商品
    void delete(Long id);
}
