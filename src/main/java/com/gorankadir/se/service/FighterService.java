package com.gorankadir.se.service;

import java.util.List;

import com.gorankadir.se.model.Fighter;

public interface FighterService {
	
	Fighter findById(Long id);
	 
	Fighter findByUsername(String username);
	Fighter findByEmail(String email);
 
    void saveFighter(Fighter fighter);
 
    void updateFighter(Fighter fighter);
 
    void deleteFighterById(Long id);
 
    void deleteAllFighters();
 
    List<Fighter> findAllFighters();
 
    boolean isFighterExist(Fighter fighter);

}
