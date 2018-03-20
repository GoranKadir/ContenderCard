package com.gorankadir.se.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gorankadir.se.model.Fighter;
import com.gorankadir.se.model.Post;
import com.gorankadir.se.model.Roles;
import com.gorankadir.se.repository.FighterRepository;
import com.gorankadir.se.service.FighterService;
import com.gorankadir.se.service.UserValidator;

@Controller
public class AdminViewController {
	
	@Autowired
	FighterService fighterService;
	
	@Autowired
	FighterRepository fighterRepository;
	
	@Autowired
	private UserValidator userValidator;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/admin")
	public String admin(Model model) {
		model.addAttribute("fighters");
		return "admin";
	}
	
	@GetMapping("/admin/users")
	public String userTable(Model model, @RequestParam(defaultValue = "0") int page){
		model.addAttribute("users",fighterRepository.findAll(new PageRequest(page, 4)));
		model.addAttribute("currentPage", page);
		return "adminusers";
	}
	
	@GetMapping("/admin/user/create")
	public String create(Model model){
		model.addAttribute("user", new Fighter());
		return "form";
	}
	
	@GetMapping(value = "/admin/user/{id}/edit")
	public String edit(@PathVariable long id, Model model) {
		model.addAttribute("user", fighterService.findById(id));
		return "form";
	}
	
	@PostMapping(path="/admin/user/{id}/edit")
public String editItem(@PathVariable Long id, Fighter fighter){
		
		Fighter fight = fighterService.findById(id);
		    fight.setUsername(fighter.getUsername());
	        fight.setFirstname(fighter.getFirstname());
	        fight.setLastname(fighter.getLastname());
	        fight.setPersonnr(fighter.getPersonnr());
	        fight.setAdress(fighter.getAdress());
	        fight.setOrt(fighter.getOrt());
	        fight.setTelefon(fighter.getTelefon());
	        fight.setEmail(fighter.getEmail());
	        fight.setKlubb(fighter.getKlubb());
		    fighterService.testUpdate(fight);
		  return "redirect:/admin/users";
	  }
	
	
	@GetMapping(value = "/admin/user/{id}/delete")
	public String delete(@PathVariable long id, RedirectAttributes redirect ) {
		fighterService.deleteFighterById(id);
		redirect.addFlashAttribute("sucess", "Delete user sucessfully");
		return "redirect:/admin/users";	
	}
	
	@PostMapping("/admin/user/save")
	public String save(@Valid Fighter fighter, BindingResult result, RedirectAttributes redirect){
		if(result.hasErrors()){
			return "form";
		}
		
		List<Roles> roles = new ArrayList<>();
		roles.add(new Roles("ROLE_USER"));
		fighter.setRole(roles);
		fighter.setNumberOfDays(0);
		fighterService.saveFighter(fighter);
		redirect.addFlashAttribute("sucess", "Saved user sucessfully");
		return "redirect:/adminusers";
		
	}

//	@GetMapping("/admin/user/search")
//	public String search(@RequestParam("s") String s, Model model){
//		if(s.equals("")){
//			return "redirect:/adminusers";
//		}
//		model.addAttribute("users", fighterService.search(s));
//		return "adminusers";
//	}
	

}
