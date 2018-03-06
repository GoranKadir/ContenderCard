package com.gorankadir.se.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorankadir.se.model.Fighter;
import com.gorankadir.se.model.Roles;
import com.gorankadir.se.service.CustomErrorType;
import com.gorankadir.se.service.FighterService;

@RestController
@RequestMapping(value = "/api")
public class FighterRestController {
	public static final Logger logger = LoggerFactory.getLogger(FighterRestController.class);
	
	@Autowired
    FighterService fighterService;
	
	   // -------------------Retrieve All Users---------------------------------------------
	
	 @RequestMapping(value = "/fighter", method = RequestMethod.GET)
	 @JsonView(FighterRestController.class)
	    public ResponseEntity<List<Fighter>> listAllFighters() {
	        List<Fighter> fighters = fighterService.findAllFighters();
	        if (fighters.isEmpty()) {
	            return new ResponseEntity(HttpStatus.NO_CONTENT);
	            // You many decide to return HttpStatus.NOT_FOUND
	        }
	        return new ResponseEntity<List<Fighter>>(fighters, HttpStatus.OK);
	    }
	
	// -------------------Retrieve Single User------------------------------------------
	 @RequestMapping(value = "/fighter/{id}", method = RequestMethod.GET)
	 @JsonView(FighterRestController.class)
	    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
	        logger.info("Fetching User with id {}", id);
	        Fighter fighter = fighterService.findById(id);
	        if (fighter == null) {
	            logger.error("User with id {} not found.", id);
	            return new ResponseEntity(new CustomErrorType("User with id " + id 
	                    + " not found"), HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<Fighter>(fighter, HttpStatus.OK);
	    }
	 
	 // -------------------Create a User-------------------------------------------
	 @RequestMapping(value = "/fighter", method = RequestMethod.POST)
	    public ResponseEntity<?> createFighter(@RequestBody Fighter fighter, UriComponentsBuilder ucBuilder) {
		 List<Roles> roles = new ArrayList<>();
		 roles.add(new Roles("ROLE_USER"));
			fighter.setRole(roles);
	        logger.info("Creating User : {}", fighter);
	 
	        if (fighterService.isFighterExist(fighter)) {
	            logger.error("Unable to create. A Fighter with username {} already exist", fighter.getUsername());
	            return new ResponseEntity(new CustomErrorType("Unable to create. A Fighter with name " + 
	            fighter.getUsername() + " already exist."),HttpStatus.CONFLICT);
	        }
	        fighterService.saveFighter(fighter);
	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/api/fighter/{id}").buildAndExpand(fighter.getId()).toUri());
	        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	    }
	 
	// ------------------- Update a User ------------------------------------------------
	 @RequestMapping(value = "/fighter/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<?> updateFighter(@PathVariable("id") long id, @RequestBody Fighter fighter) {
	        logger.info("Updating User with id {}", id);
	 
	        Fighter currentFighter = fighterService.findById(id);
	 
	        if (currentFighter == null) {
	            logger.error("Unable to update. Fighter with id {} not found.", id);
	            return new ResponseEntity(new CustomErrorType("Unable to upate. Fighter with id " + id + " not found."),
	                    HttpStatus.NOT_FOUND);
	        }
	    
	        currentFighter.setUsername(fighter.getUsername());
	        currentFighter.setFirstname(fighter.getFirstname());
	        currentFighter.setLastname(fighter.getLastname());
	        currentFighter.setPersonnr(fighter.getPersonnr());
	        currentFighter.setAdress(fighter.getAdress());
	        currentFighter.setOrt(fighter.getOrt());
	        currentFighter.setTelefon(fighter.getTelefon());
	        currentFighter.setEmail(fighter.getEmail());
	        currentFighter.setKlubb(fighter.getKlubb());
	        currentFighter.setPassword(fighter.getPassword());
	        currentFighter.setPasswordConfirm(fighter.getPasswordConfirm());
	        
	        fighterService.updateFighter(currentFighter);
	        return new ResponseEntity<Fighter>(currentFighter, HttpStatus.OK);
	    }
	 
	 
	 
	// ------------------- Delete a User-----------------------------------------
	 
	    @RequestMapping(value = "/fighter/{id}", method = RequestMethod.POST)
	    public ResponseEntity<?> deleteFighter(@PathVariable("id") long id) {
	        logger.info("Fetching & Deleting Fighter with id {}", id);
	 
	        Fighter fighter = fighterService.findById(id);
	        if (fighter == null) {
	            logger.error("Unable to delete. Fighter with id {} not found.", id);
	            return new ResponseEntity(new CustomErrorType("Unable to delete. Fighter with id " + id + " not found."),
	                    HttpStatus.NOT_FOUND);
	        }
	        fighterService.deleteFighterById(id);
	        return new ResponseEntity<Fighter>(HttpStatus.NO_CONTENT);
	    }
	    
	 // ------------------- Delete All Users-----------------------------
	    
	    @RequestMapping(value = "/fighter", method = RequestMethod.DELETE)
	    public ResponseEntity<Fighter> deleteAllFighters() {
	        logger.info("Deleting All Fighters");
	 
	        fighterService.deleteAllFighters();
	        return new ResponseEntity<Fighter>(HttpStatus.NO_CONTENT);
	    }

}
