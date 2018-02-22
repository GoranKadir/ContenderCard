package com.gorankadir.se.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.gorankadir.se.model.Fighter;
import com.gorankadir.se.model.Post;
import com.gorankadir.se.repository.FighterRepository;
import com.gorankadir.se.service.FighterService;
import com.gorankadir.se.service.PostService;

@Controller
public class PostViewController {
	@Autowired
	FighterService fighterService;
	
	@Autowired
	FighterRepository fighterRepository;

	@Autowired
	PostService postService;

	@RequestMapping(value = "/createposts")
	public String createBlogg(Model model) {
		model.addAttribute("newPost", new Post());
		return "createpost";
	}

	@PostMapping(value = "/book/save")
	public String create(Model model, @Valid @ModelAttribute("newPost") Post newPost){
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String username = auth.getName();
	Fighter fighter = fighterService.findByUsername(username);
	fighter.addPosts(newPost);
		fighterRepository.save(fighter);
	        return "createpost";
}
	
	@RequestMapping(value = "/posts")
	public String viewPost(Model model){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Post[]> posts = restTemplate.getForEntity("http://localhost:8080/api/posts", Post[].class);
		model.addAttribute("info", posts.getBody());
		return "posts";
	}
	
}