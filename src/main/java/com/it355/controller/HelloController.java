package com.it355.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView defaultPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("message", "Ovo je strana kojoj svi mogu da pristupe!");
		model.setViewName("hello");
		
		return model;
	}
	
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("message", "Ovo je strana za administratore!");
		model.setViewName("admin");
		
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value="error", required = false) String error, @RequestParam(value="logout", required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if(error != null) {
			model.addObject("error", "Losi login podaci!");
		}
		
		if(logout != null) {
			model.addObject("msg", "Izlogovani ste.");
		}
		model.setViewName("login");
		
		return model;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView model = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails)auth.getPrincipal();
			System.out.println(userDetail);
			
			model.addObject("username", userDetail.getUsername());
		}
		
		model.setViewName("403");
		
		return model;
	}
}
