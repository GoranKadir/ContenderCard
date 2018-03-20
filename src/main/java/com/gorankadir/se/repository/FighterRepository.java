package com.gorankadir.se.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gorankadir.se.model.Fighter;

@Repository
public interface FighterRepository extends JpaRepository<Fighter, Long> {
	public Fighter findByEmail(String email);
	public Fighter findByUsername(String username);
	
	//List<Fighter> findByNameContaining(String q);
	
}
