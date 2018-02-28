package com.gorankadir.se.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.gorankadir.se.model.Fighter;

@Component
public class UserValidator implements Validator {
	
	@Autowired
    private FighterService fighterService;
	
	 @Override
	    public boolean supports(Class<?> aClass) {
	        return Fighter.class.equals(aClass);
	    }

	    @Override
	    public void validate(Object o, Errors errors) {
	    	Fighter fighter = (Fighter) o;

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
	        if (fighter.getUsername().length() < 6 || fighter.getUsername().length() > 32) {
	            errors.rejectValue("username", "Size.userForm.username");
	        }
	        
	        if (fighterService.findByUsername(fighter.getUsername()) != null) {
	            errors.rejectValue("username", "Duplicate.userForm.username");
	        }
	        
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
	        if (fighterService.findByEmail(fighter.getEmail()) != null) {
	            errors.rejectValue("email", "Duplicate.userForm.email");
	        }
	        

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
	        if (fighter.getPassword().length() < 8 || fighter.getPassword().length() > 32) {
	            errors.rejectValue("password", "Size.userForm.password");
	        }

	        if (!fighter.getPasswordConfirm().equals(fighter.getPassword())) {
	            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
	        }
	        
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personnr", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adress", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ort", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telefon", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "klubb", "NotEmpty");
	      
	        
	    }

}
