package com.example.demo.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "password")  // ✅ 新增這行
    private String password;

 // ✅ 使用者角色（user / admin）
    @Column(name = "role", nullable = false)
    private String role = "user"; // 預設為一般使用者

    // ✅ 建立時間
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // ✅ 修改時間
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    // ✅ 建立時自動填入
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    // ✅ 更新時自動更新
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
    
    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "user"; // 註冊時預設角色
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }   // ✅ 新增
    public void setPassword(String password) { this.password = password; }  // ✅ 新增

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
