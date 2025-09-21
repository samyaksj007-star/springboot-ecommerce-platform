package com.spring.sakura.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.sakura.entities.Users;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	
	@GetMapping("/admin/home")
	public String adminHandler(HttpSession session, Model model){
		
		Users user = (Users) session.getAttribute("loggedInUser");
	    if (user == null || !user.getRole().equals("ADMIN")) {
	        return "redirect:/login"; // not authorized
	    }

	    model.addAttribute("admin", user);
		return "admin";
	}

}
