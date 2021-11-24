package com.mpsib.messageboard.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mpsib.messageboard.domain.NewUserDetails;

@Controller
public class RegistrationController {
	private UserDetailServiceImpl userService;
	
	public RegistrationController(UserDetailServiceImpl userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("newuser", new NewUserDetails());
		return "signup";
	}

	@PostMapping("/signup")
	public String signupPage(NewUserDetails newUser) {
		userService.save(newUser);
		return "redirect:/?reg";
	}
}
