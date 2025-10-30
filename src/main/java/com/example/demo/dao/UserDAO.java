package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.User;

public interface UserDAO {
	List<User> findAll();

	User findById(Long id);

	void save(User user);

	void delete(Long id);

	User findByNameAndPassword(String name, String password);
	
	User findByEmail(String email);

}
