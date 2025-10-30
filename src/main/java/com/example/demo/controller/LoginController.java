package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /** 顯示登入畫面 */  
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // /WEB-INF/views/login.html
    }

    /** 處理登入 */
    @PostMapping("/login")
    public String doLogin(@RequestParam("name") String name,
                          @RequestParam("password") String password,
                          HttpSession session,
                          Model model) {
        User user = userService.login(name, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            
            return "redirect:/products"; // 登入成功
        } else {
            model.addAttribute("error", "帳號或密碼錯誤！");
            return "login";
        }
    }

    /** 登出 */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/products";
    }
    /** 顯示註冊頁 */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /** 處理註冊 */
    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {

        User user = new User(name, email, password);
        userService.saveUser(user);

        model.addAttribute("message", "註冊成功！請登入");
        return "login";
    }

    /** 忘記密碼畫面 */
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    /** 查詢密碼 */
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("result", "查無此帳號");
        } else {
            model.addAttribute("result", "您的密碼為： " + user.getPassword());
        }

        return "forgot-password";
    }
}
