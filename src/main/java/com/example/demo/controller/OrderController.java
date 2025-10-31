package com.example.demo.controller;

import com.example.demo.model.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    // ✅ 提交訂單
    @PostMapping("/order/submit")
    public String submitOrder(HttpSession session, Model model,
                              String receiverName,
                              String receiverPhone,
                              String receiverAddress) {

        // 從 Session 取得購物車
        Cart cart = (Cart) session.getAttribute("cart");

        // 如果購物車沒有東西 → 回購物車
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        // ✅ 這裡目前先用「毫秒」模擬訂單編號（之後會改成寫進資料庫）
        long orderId = System.currentTimeMillis();

        // ✅ 將 orderId 傳給成功頁面
        model.addAttribute("orderId", orderId);

        // ✅ 清空購物車
        session.removeAttribute("cart");

        // ✅ 導向成功頁面 (order-success.html)
        return "order-success";
    }
}
