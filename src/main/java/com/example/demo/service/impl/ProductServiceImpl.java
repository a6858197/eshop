package com.example.demo.service.impl;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public List<Product> getAllProducts() {
		return productDAO.findAll();
	}

	// ✅ 實作分類查詢
	@Override
	public List<Product> getProductsByCategory(String category) {
		return productDAO.findByCategory(category);
	}

	@Override
	public List<Product> searchProducts(String keyword) {
		return productDAO.searchByKeyword(keyword);
	}

	@Override
	public Product getProductById(Long id) {
		return productDAO.findById(id);
	}

	@Override
	public void saveProduct(Product product) {
		productDAO.save(product);
	}

	@Override
	public void updateProduct(Long id, Product product) {
		productDAO.update(id, product);
	}

	@Override
	public void deleteProduct(Long id) {
		productDAO.delete(id);
	}

}
