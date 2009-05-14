package com.jjpeople.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jjpeople.bean.User;
import com.jjpeople.dao.UserDAO;

public class UserReportController implements Controller {
	private UserDAO userDAO;
	public static Logger logger = Logger.getLogger(LoginController.class);
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String format = request.getParameter("format");
		List<User> users = userDAO.getAllusers();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("users", users);
		model.put("format", format);
		
		logger.info("Number of users: "+users.size());
		return new ModelAndView("usersList-report", model);
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
