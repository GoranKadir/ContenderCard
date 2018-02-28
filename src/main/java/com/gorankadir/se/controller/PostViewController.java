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
import com.gorankadir.se.service.PostValidator;

@Controller
public class PostViewController {
	@Autowired
	FighterService fighterService;

	@Autowired
	FighterRepository fighterRepository;

	@Autowired
	PostService postService;
	
	@Autowired
    private PostValidator postValidator;

	@RequestMapping(value = "/createposts")
	public String createBlogg(Model model) {
		model.addAttribute("newPost", new Post());
		return "createpost";
	}

	@PostMapping(value = "/book/save")
	public String create(@Valid @ModelAttribute(name = "newPost") Post newPost, BindingResult bindingResult) {
		postValidator.validate(newPost, bindingResult);
		
		if(bindingResult.hasErrors()){
			return "createpost";
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Fighter fighter = fighterService.findByUsername(username);
		fighter.addPosts(newPost);
		fighterRepository.save(fighter);
		return "redirect:/posts";
	}
	
	@RequestMapping(value = "/posts")
	public String viewPost(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Post[]> posts = restTemplate.getForEntity("http://localhost:8080/api/posts", Post[].class);
		model.addAttribute("info", posts.getBody());
		return "posts";
	}

	@RequestMapping(value = "/posts/{id}")
	public String viewOnePost(@PathVariable Long id, Model model) {
		model.addAttribute("post", postService.findById(id));
		return "viewpost";
	}
	
	@RequestMapping(value = "/posts/edit/{id}")
	public String editOnePost(@PathVariable Long id, Model model) {
		model.addAttribute("newPost", postService.findById(id));
		return "createpost";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}