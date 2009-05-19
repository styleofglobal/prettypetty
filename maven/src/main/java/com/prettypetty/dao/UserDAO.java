package com.prettypetty.dao;

import java.util.List;

import com.prettypetty.bean.User;
import com.prettypetty.dao.exceptions.NotExistException;

public interface UserDAO {
	public void saveUser(User user);
	public void deleteUser(User user);
	public User findUserByName(String username) throws NotExistException;
	@SuppressWarnings("unchecked")
	public List<User> getAllusers();
}
