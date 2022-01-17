package org.generation.italy.controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.italy.model.Ingrediente;
import org.generation.italy.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ingredienti")
public class IngredienteController {

	
	@Autowired
	private IngredienteService service;
	
	
	@GetMapping
	public String list(Model model, @RequestParam(name="keyword", required=false) String keyword) {
		List<Ingrediente> result;
		if(keyword != null && !keyword.isEmpty()) {
			result = service.findByKeywordSortedByRecent(keyword);
			model.addAttribute("keyword", keyword);
		} else {
			result = service.findAllSortByIngrediente();
		}
		model.addAttribute("list", result);
		return "/ingredienti/list";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("ingredienteObj", new Ingrediente());
		model.addAttribute("edit", false);
		return "/ingredienti/edit";
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("ingredienteObj") Ingrediente ing, 
			Model model, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
			return "/ingredienti/edit";
		}
		service.create(ing);
		return "redirect:/ingredienti";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("ingredienteObj", service.getById(id));
		return "/ingredienti/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("ingredienteObj") Ingrediente formIngrediente, 
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			return "/ingredienti/edit";
		}
		service.update(formIngrediente);
		return "redirect:/ingredienti";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		service.deleteById(id);
		return "redirect:/ingredienti";
	}
}
