package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	 /** ✅ 0) 首頁 → 自動導向 /users */
    @GetMapping("/")  // 注意這裡是 "/"
    public String home() {
        return "redirect:/users";
    }

	 /** ✅ 1) 列出所有使用者 */
	@GetMapping
	public String listUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users";
	}

	/** ✅ 2) 顯示新增表單 */
	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("user", new User());
		return "add-user";
	}

	/** ✅ 3) 儲存（存儲）新使用者 */
	@PostMapping("/save")
	public String saveUser(@ModelAttribute User user) {
		userService.saveUser(user);
		return "redirect:/users";
	}
	
	 /** ✅ 4) 顯示編輯表單 */
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "edit-user"; // 對應 /WEB-INF/views/edit-user.html
	}

	/** ✅ 5) 更新使用者資料 */
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
		userService.updateUser(id, user);
		return "redirect:/users";
	}
	
	/** ✅ 6) 刪除使用者 */
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/users";
	}
}