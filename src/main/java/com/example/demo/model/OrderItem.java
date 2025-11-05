package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "order_item") // ✅ 保留你原本的資料表名稱
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// ✅ 關聯訂單
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	// ✅ 關聯商品（下單時從 Product 取資料）
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	// ✅ 下單時的單價（快照價）
	private Double price;

	// ✅ 數量
	private Integer quantity;

	// ✅ 小計（= price * quantity）
	private Double subtotal;

	public OrderItem() {
	}

	// ✅ 這個建構子寫法安全，不會 NPE
	public OrderItem(Product product, Integer quantity, Double price) {
		this.product = product;
		this.price = price != null ? price : 0.0;
		this.quantity = quantity != null ? quantity : 0;
		this.subtotal = this.price * this.quantity;
	}

	// --- Getter / Setter ---
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = (price != null ? price : 0.0);
		recalcSubtotal();
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = (quantity != null ? quantity : 0);
		recalcSubtotal();
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	// ✅ 自動計算小計（避免 NPE）
	private void recalcSubtotal() {
		if (this.price != null && this.quantity != null) {
			this.subtotal = this.price * this.quantity;
		} else {
			this.subtotal = 0.0;
		}
	}

	@Override
	public String toString() {
		return "OrderItem{id=" + id + ", product=" + (product != null ? product.getName() : "null") + ", quantity="
				+ quantity + ", price=" + price + ", subtotal=" + subtotal + '}';
	}
}
