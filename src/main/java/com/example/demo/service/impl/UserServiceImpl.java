package com.example.demo.service.impl;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	@Override
	public void updateUser(Long id, User updatedUser) {
		User existingUser = userDao.findById(id);
		if (existingUser != null) {
			existingUser.setName(updatedUser.getName());
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setPassword(updatedUser.getPassword());
			userDao.save(existingUser);
		}
	}

	@Override
	public User login(String username, String password) {
		return userDao.findByNameAndPassword(username, password);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		userDao.delete(id);
	}
	
	  @Override
	    public User findByEmail(String email) {
	        return userDao.findByEmail(email);
	    }
	
}
