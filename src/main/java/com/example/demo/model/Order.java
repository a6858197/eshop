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

    // ✅ 使用 ManyToOne 關聯會員
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) 
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private String status;

    private Double total;

    // ✅ 新增付款相關資訊
    private String paymentMethod; // ATM / CREDIT / COD
    private String address;       // 收件地址
    private String invoiceType;   // 二聯式 / 三聯式 / 載具

    // ✅ 一個訂單多筆明細
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getInvoiceType() { return invoiceType; }
    public void setInvoiceType(String invoiceType) { this.invoiceType = invoiceType; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { 
        this.items = items;
        calculateTotal(); // ✅ 設定明細時自動重新計算總額
    }

    // ✅ 計算訂單總額
    public void calculateTotal() {
        if (items != null && !items.isEmpty()) {
            this.total = items.stream().mapToDouble(OrderItem::getSubtotal).sum();
        } else {
            this.total = 0.0;
        }
    }

    @Override
    public String toString() {
        return "Order{id=" + id +
                ", user=" + (user != null ? user.getId() : null) +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", total=" + total +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", address='" + address + '\'' +
                ", invoiceType='" + invoiceType + '\'' +
                '}';
    }
}
