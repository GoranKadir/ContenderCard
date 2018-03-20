package com.gorankadir.se.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gorankadir.se.model.Fighter;
import com.gorankadir.se.repository.FighterRepository;

@Service("fighterService")
@Transactional
public class FighterServiceImpl implements FighterService {
	
	@Autowired
	private FighterRepository fighterRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Fighter findById(Long id) {
		
		return fighterRepository.findOne(id);
	}

	@Override
	public Fighter findByUsername(String username) {
		// TODO Auto-generated method stub
		return fighterRepository.findByUsername(username);
	}

	@Override
	public Fighter findByEmail(String email) {
		// TODO Auto-generated method stub
		return fighterRepository.findByEmail(email);
	}

	@Override
	public void saveFighter(Fighter fighter) {
		fighter.setPassword(bCryptPasswordEncoder.encode(fighter.getPassword()));
		fighter.setPasswordConfirm(bCryptPasswordEncoder.encode(fighter.getPasswordConfirm()));
		fighterRepository.save(fighter);
		
	}

	@Override
	public void updateFighter(Fighter fighter) {
		saveFighter(fighter);
	}
	
	

	@Override
	public void deleteFighterById(Long id) {
		fighterRepository.delete(id);
		
	}

	@Override
	public void deleteAllFighters() {
		fighterRepository.deleteAll();
		
	}

	@Override
	public List<Fighter> findAllFighters() {
		// TODO Auto-generated method stub
		return fighterRepository.findAll();
	}

	@Override
	public boolean isFighterExist(Fighter fighter) {
		// TODO Auto-generated method stub
		return findByUsername(fighter.getUsername()) !=null;
	}

	@Override
	public void testUpdate(Fighter fighter) {
		fighterRepository.save(fighter);
		
	}

	@Override
	public long countFighers() {
		return fighterRepository.count();
	}

	@Override
	public void deleteOneFighterrById(Fighter fighter) {
		
	}

//	@Override
//	public List<Fighter> search(String q) {
//		return fighterRepository.findByNameContaining(q) ;
//	}

}
