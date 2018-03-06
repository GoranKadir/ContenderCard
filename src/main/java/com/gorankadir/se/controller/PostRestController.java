package com.gorankadir.se.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorankadir.se.model.Fighter;
import com.gorankadir.se.model.Post;
import com.gorankadir.se.service.CustomErrorType;
import com.gorankadir.se.service.FighterService;
import com.gorankadir.se.service.PostService;

@RestController
@RequestMapping(value = "/api")
public class PostRestController {
public static final Logger logger = LoggerFactory.getLogger(FighterRestController.class);
	
	@Autowired
    PostService postService;
	
	@Autowired
    FighterService fighterService;
	
	   // -------------------Retrieve All Posts---------------------------------------------
	 @RequestMapping(value = "/posts", method = RequestMethod.GET)
	 @JsonView(PostRestController.class)
	    public ResponseEntity<List<Post>> listAllPosts() {
	        List<Post> posts = postService.findAllPost();
	        if (posts.isEmpty()) {
	            return new ResponseEntity(HttpStatus.NO_CONTENT);
	            // You many decide to return HttpStatus.NOT_FOUND
	        }
	        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	    }
	
	// -------------------Retrieve Single Post------------------------------------------
	 @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
	 @JsonView(PostRestController.class)
	    public ResponseEntity<?> getPost(@PathVariable("id") long id) {
	        logger.info("Fetching User with id {}", id);
	        Post post = postService.findById(id);
	        if (post == null) {
	            logger.error("User with id {} not found.", id);
	            return new ResponseEntity(new CustomErrorType("User with id " + id 
	                    + " not found"), HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<Post>(post, HttpStatus.OK);
	    }
	 
	 // -------------------Create a Post-------------------------------------------
	 @RequestMapping(value = "/post", method = RequestMethod.POST)
	    public ResponseEntity<Void> createPost(@RequestBody Post post) {
	        postService.createPost(post);
	        HttpHeaders headers = new HttpHeaders();
	        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	    }
	 	
	 
	// ------------------- Update a User ------------------------------------------------
	 @RequestMapping(value = "/post/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<?> updatePost(@PathVariable("id") long id, @RequestBody Post post) {
	        logger.info("Updating User with id {}", id);
	 
	        Post currentPost = postService.findById(id);
	    
	        currentPost.setTitle(post.getTitle());
	        currentPost.setBody(post.getBody());
	        currentPost.setAuthor(post.getAuthor());
	        currentPost.setDate(post.getDate());
	        postService.editPost(currentPost);
	        return new ResponseEntity<Post>(currentPost, HttpStatus.OK);
	    }
	 
	 
	 
	// ------------------- Delete a User-----------------------------------------
	 
	    @RequestMapping(value = "/post/{id}", method = RequestMethod.POST)
	    public ResponseEntity<?> deletePost(@PathVariable("id") long id) {
	      
	        Post post = postService.findById(id);
	        if (post == null) {
	            logger.error("Unable to delete. Fighter with id {} not found.", id);
	            return new ResponseEntity(new CustomErrorType("Unable to delete. Fighter with id " + id + " not found."),
	                    HttpStatus.NOT_FOUND);
	        }
	        postService.deleteById(post);
	        return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
	    }
	 
}
