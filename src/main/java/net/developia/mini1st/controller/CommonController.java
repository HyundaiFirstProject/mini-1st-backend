package net.developia.mini1st.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.developia.mini1st.security.HasRoleUser;

@Controller
public class CommonController {
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		model.addAttribute("msg","access denied");
	}
	
	@GetMapping("/login")
	public void loginInput(String error, String logout, Model model) {
		if(error != null) {
			model.addAttribute("error", "Login Error Check Your Account");
		}
		if(logout != null) {
			model.addAttribute("logout", "Logout");
		}
	}
	
	@HasRoleUser
	@GetMapping("/logout")
	public void logoutGET() {
		System.out.print("logout");
	}
}
