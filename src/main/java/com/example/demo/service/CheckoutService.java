package com.example.demo.service;

import com.example.demo.model.User;

public interface CheckoutService {

    /**
     * 結帳處理，將購物車內容轉為訂單
     * @param user 會員
     * @param paymentMethod 付款方式 (ATM / CREDIT / COD)
     * @param address 收件地址
     * @param invoiceType 發票類型 (二聯式 / 三聯式 / 載具)
     * @return 建立成功後的訂單編號
     */
    Long checkout(User user, String paymentMethod, String address, String invoiceType);
}
