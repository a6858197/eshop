package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 多個 CartItem 屬於同一個 Cart
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // ✅ 一筆 CartItem 對應一個 Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    // ✅ 金額快照（加入時的價格，避免商品改價）
    @Column(name = "price_snapshot", nullable = false)
    private Double priceSnapshot;

    public CartItem() {
        // ✅ JPA 需要無參建構子
    }

    // ✅ 你缺的建構子（加入購物車使用）
    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;

        // ✅ 加入當下的價格快照（重點）
        this.priceSnapshot = product.getPrice();
    }

    public Long getId() { return id; }
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Double getPriceSnapshot() { return priceSnapshot; }
    public void setPriceSnapshot(Double priceSnapshot) { this.priceSnapshot = priceSnapshot; }

    // ✅ 小計（不存 DB）
    @Transient
    public double getSubtotal() {
        return priceSnapshot * quantity;
    }
}
