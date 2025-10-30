package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders") // 對應資料表 orders
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // 使用者ID，可選，如果有會員系統
    private Date orderDate;
    private String status;
    private Double total;

    // 一個訂單有多個明細
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    // --- Constructors ---
    public Order() {
        this.orderDate = new Date();
        this.status = "待付款";
        this.total = 0.0;
    }

    // --- Getters / Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    // 計算訂單總額
    public void calculateTotal() {
        if(items != null && !items.isEmpty()) {
            this.total = items.stream().mapToDouble(OrderItem::getSubtotal).sum();
        } else {
            this.total = 0.0;
        }
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", userId=" + userId + ", orderDate=" + orderDate +
                ", status='" + status + '\'' + ", total=" + total + '}';
    }
}
