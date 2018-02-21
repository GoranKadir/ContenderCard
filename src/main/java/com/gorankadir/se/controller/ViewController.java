package com.gorankadir.se.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import com.gorankadir.se.model.Fighter;
import com.gorankadir.se.model.Post;
import com.gorankadir.se.model.Roles;
import com.gorankadir.se.service.FighterService;
import com.gorankadir.se.service.SecurityService;
import com.gorankadir.se.service.UserValidator;

@Controller
public class ViewController {
		
    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private FighterService fighterService;
    
   
    @PostMapping("/registration")
	public String registerForm(@ModelAttribute(name = "userForm")Fighter userForm, BindingResult bindingResult) {
    	userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        
		RestTemplate restTemplate = new RestTemplate();
		List<Roles> roles = new ArrayList<>();
		roles.add(new Roles("ROLE_USER"));
		userForm.setRole(roles);
		restTemplate.postForEntity("http://localhost:8080/api/fighter", userForm, Fighter.class);
		securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
		return "index";
	}
    
    @GetMapping("/registration")
	public String getCustomerList(Model model) {
		model.addAttribute("userForm", new Fighter());
		return "registration";
	}


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin")
    public String admin(Model model) {
    	RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Fighter[]> fighter = restTemplate.getForEntity("http://localhost:8080/api/fighter", Fighter[].class);
		model.addAttribute("info", fighter.getBody());
        return "admin";
    }
    
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Fighter user = fighterService.findByUsername(username);
		model.addAttribute("info", user);
        return "index";
    }
}