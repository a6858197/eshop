package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "products") // 對應資料庫中的 products 表
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 主鍵 ID

	@Column(name = "name", nullable = false, length = 100)
	private String name; // 商品名稱

	@Column(name = "description", length = 255)
	private String description; // 商品描述

	@Column(name = "price", nullable = false)
	private Double price; // 商品價格

	@Column(name = "stock", nullable = false)
	private Integer stock; // 商品庫存

	@Column(name = "category", length = 50)
	private String category; // 商品分類

	@Column(name = "image", length = 255)
	private String image; // 商品圖片路徑

	// 無參構造函數（Hibernate 需要）
	public Product() {
	}

	// 有參構造函數
	public Product(String name, String description, Double price, Integer stock, String category, String image) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.category = category;
		this.image = image;
	}

	// Getter 與 Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Product{id=" + id + ", name='" + name + '\'' + ", price=" + price + ", stock=" + stock + ", category='"
				+ category + '\'' + ", image='" + image + '\'' + '}';
	}
}
