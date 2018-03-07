package com.gorankadir.se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gorankadir.se.model.Fighter;
import com.gorankadir.se.service.FighterService;

@Controller
public class ContestCardController {
	
	@Autowired
	FighterService fighterService;
	@GetMapping("/tavlingskort")
	public String contectCard(Model model){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Fighter user = fighterService.findByUsername(username);
		if (!user.isLimitedAccess()){
		model.addAttribute("info", user);
		return "contestcard";
		}
		return "redirect:/";
	}

}
