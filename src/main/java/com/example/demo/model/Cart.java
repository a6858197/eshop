package com.example.demo.model;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public Long getId() { return id; }

    public User getMember() { return member; }
    public void setMember(User member) { this.member = member; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    @Transient
    public double getTotal() {
        return items == null ? 0 :
                items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    // ✅ 加入商品
    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        CartItem newItem = new CartItem(this, product, quantity);
        items.add(newItem);
    }

    // ✅ 更新數量（數量 <= 0 則刪除）
    public void updateQuantity(Long productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    items.remove(item);
                }
                return;
            }
        }
    }

    // ✅ 移除商品
    public void removeProduct(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }
}
