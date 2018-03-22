package com.gorankadir.se.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		
		registry.addViewController("/welcome").setViewName("hello");
		registry.addViewController("/").setViewName("hello");
		registry.addViewController("/tavlingskort").setViewName("contestcard");
		//registry.addViewController("/profile").setViewName("profile");
		
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/membership").setViewName("registerformula");
		
		registry.addViewController("/posts").setViewName("posts");
		registry.addViewController("/createposts").setViewName("createpost");
		registry.addViewController("//posts/{id}").setViewName("viewpost");

		registry.addViewController("/registration").setViewName("registration");
		registry.addViewController("/registration").setViewName("index");
		
		registry.addViewController("/profile").setViewName("profile");
		registry.addViewController("/edit/profile/{id}").setViewName("editprofile");
		
		registry.addViewController("/aboutus").setViewName("aboutus");
		
		
	}

}
