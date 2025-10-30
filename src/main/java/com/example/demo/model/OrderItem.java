package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 對應訂單
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // 對應商品
    private Long productId;
    private Integer quantity;
    private Double price;    // 下單時的單價
    private Double subtotal; // price * quantity

    public OrderItem() {}

    public OrderItem(Long productId, Integer quantity, Double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = price * quantity;
    }

    // --- Getter / Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity; 
        this.subtotal = this.price * quantity; // 自動更新 subtotal
    }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { 
        this.price = price; 
        this.subtotal = this.price * this.quantity; 
    }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return "OrderItem{id=" + id + ", productId=" + productId +
               ", quantity=" + quantity + ", price=" + price + ", subtotal=" + subtotal + '}';
    }
}
