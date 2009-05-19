package com.prettypetty.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.prettypetty.bean.User;
import com.prettypetty.dao.UserDAO;

public class LoginController implements Controller {
	private UserDAO userDAO;
	public static Logger logger = Logger.getLogger(LoginController.class);
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		
		logger.info("username: "+username);
		
		User user = userDAO.findUserByName(username);
		ModelAndView mv = new ModelAndView("login");
		logger.info("User role: "+user.getRole());
		mv.addObject("role", user.getRole());
		return mv;
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
}
