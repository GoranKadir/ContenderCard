package com.gorankadir.se.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.gorankadir.se.model.Post;

@Component
public class PostValidator implements Validator{

	@Override
	public boolean supports(Class<?> aClass) {
		return Post.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Post post = (Post) o;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "body", "NotEmpty");
		
		
	}

}
