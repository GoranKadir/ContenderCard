package com.gorankadir.se.service;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gorankadir.se.model.Fighter;
import com.gorankadir.se.model.Roles;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    FighterService fighterService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Fighter user = fighterService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        Collection<GrantedAuthority> roles = new ArrayList<>();
        
        for(Roles role : user.getRole()){
        	roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        
        return new User(user.getUsername(), user.getPassword(), roles);
    }

}
