package com.prettypetty.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.prettypetty.bean.User;
import com.prettypetty.dao.UserDAO;

public class ListUsersController implements Controller {
	private UserDAO userDAO;
	public static Logger logger = Logger.getLogger(LoginController.class);
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		List<User> users = userDAO.getAllusers();
		logger.info("Number of users: "+users.size());
		return new ModelAndView("listUsers","users", users);
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
