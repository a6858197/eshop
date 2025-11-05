package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String paymentType;  // ATM / CREDIT / LINEPAY...
    private Double paidAmount;
    private String provider;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    // Getters & Setters
    public Long getPaymentId() { return paymentId; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public Double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(Double paidAmount) { this.paidAmount = paidAmount; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public Date getCreatedAt() { return createdAt; }
}
