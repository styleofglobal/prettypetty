package com.prettypetty.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;

public class GenericController extends UrlFilenameViewController{
	@Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
            return super.handleRequest(request,response).addObject("title", 
                    request.getRequestURI());
    }
}
