package com.example.demo.service.impl;

import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.OrderItemDAO;
import com.example.demo.model.*;
import com.example.demo.service.CartService;
import com.example.demo.service.CheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CartService cartService; // 取得會員購物車

    @Autowired
    private OrderDAO orderDAO; // 儲存訂單主檔

    @Autowired
    private OrderItemDAO orderItemDAO; // 儲存訂單明細

    @Override
    public Long checkout(User user, String paymentMethod, String address, String invoiceType) {

        // ✅ 抓會員購物車
        Cart cart = cartService.getCartByMember(user);
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("購物車為空，無法結帳");
        }

        // ✅ 建立訂單主檔
        Order order = new Order();
        order.setUser(user);
        order.setStatus("待付款");
        order.setTotal(0.0);
        order.setOrderDate(new java.util.Date());

        // ✅ 設定付款與發票資訊
        order.setPaymentMethod(paymentMethod);
        order.setAddress(address);
        order.setInvoiceType(invoiceType);

        // ✅ 將購物車項目轉為訂單明細
        List<CartItem> cartItems = cart.getItems();
        double total = 0.0;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            // 先不存明細，先掛進 order，之後 cascade 自動存
            order.getItems().add(orderItem);

            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        // ✅ 設定訂單總額
        order.setTotal(total);

        // ✅ 儲存訂單（連同訂單明細）
        orderDAO.save(order);

        // ✅ 清除購物車
        cart.getItems().clear();
        cartService.save(cart);

        // ✅ 回傳訂單編號
        return order.getId();
    }
}
