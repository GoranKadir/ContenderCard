package com.gorankadir.se.service;

import java.util.List;

import com.gorankadir.se.model.Fighter;

public interface FighterService {
	
	Fighter findById(Long id);
	 
	Fighter findByUsername(String username);
	Fighter findByEmail(String email);
 
    void saveFighter(Fighter fighter);
  
    void updateFighter(Fighter fighter);
    
    void testUpdate(Fighter fighter);
 
    void deleteOneFighterrById(Fighter fighter);
    
    void deleteFighterById(Long id);
 
    void deleteAllFighters();
 
    List<Fighter> findAllFighters();
    
    //List<Fighter> search(String q);
 
    boolean isFighterExist(Fighter fighter);
    
    long countFighers();
    

}
