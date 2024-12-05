package com.bway.springproject.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.springproject.model.User;
import com.bway.springproject.service.UserService;
import com.bway.springproject.utils.VerifyRecaptcha;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String getLogin() {
		
		return "LoginForm";
	}
	
	@GetMapping("/signup")
	public  String getSignup() {
		
		return "SignupForm";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute User user) {
		
		userService.userSignup(user);
		
		return "LoginForm";
	}

	@PostMapping("/login")
	public String postLogin(@ModelAttribute User user,Model model,HttpSession session,@RequestParam("g-recaptcha-response") String greCode) throws IOException {
		
		   if(VerifyRecaptcha.verify(greCode)) {
		
			  User usr = userService.userLogin(user.getUsername(), user.getPassword());
			  
			  if(usr != null) {
				  
				  session.setAttribute("activeUser", usr);
				  session.setMaxInactiveInterval(300);
				  
				 // model.addAttribute("uname",usr.getFname());
				  return "Home";
			  }else {
				      model.addAttribute("message", "user not found!!");
					return "LoginForm";
			  }
	    } 
		    
		  model.addAttribute("message", "u R robot !!");
		return "LoginForm";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();//kill session
		return "LoginForm";
	}
	
	@GetMapping("/profile")
	public String getProfile() {
		
		return "Profile";
	}
}
