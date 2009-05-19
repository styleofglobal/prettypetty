package com.prettypetty.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.prettypetty.bean.User;
import com.prettypetty.dao.UserDAO;

public class AddUserController extends SimpleFormController{
	private UserDAO userDAO;
	public static Logger logger = Logger.getLogger(AddUserController.class);
	
	public AddUserController(){
		logger.info("<<<<<<<<<<<<<<<setting defaults>>>>>>>>>");
		setCommandClass(User.class);
		setCommandName("user");
		setFormView("addUser");
	}
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		User user = (User) command;
		
		logger.info("User name:"+ user.getUsername()+" UserRole: "+user.getRole());
		userDAO.saveUser(user);
		
		return new ModelAndView("success");
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
