package org.generation.italy.controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.italy.model.Pizza;
import org.generation.italy.service.IngredienteService;
import org.generation.italy.service.PizzaService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

	@Autowired
	private PizzaService service;
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@GetMapping
	public String list(Model model, @RequestParam(name="keyword", required=false) String keyword) {
		List<Pizza> result;
		if(keyword != null && !keyword.isEmpty()) {
			result = service.findByKeywordSortedByRecent(keyword);
			model.addAttribute("keyword", keyword);
		} else {
			result = service.findAllSortedByNome();
		}
		model.addAttribute("list", result);
		return "/pizza/list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id , Model model) {
		model.addAttribute("pizza", service.getById(id));
		return "/pizza/detail";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("pizzaObj", new Pizza());
		model.addAttribute("ingredienteList", ingredienteService.findAllSortByIngrediente());
		return "/pizza/edit";
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model,  RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
			model.addAttribute("ingredienteList", ingredienteService.findAllSortByIngrediente());
			return "/pizza/edit";
		}
		
		service.create(formPizza);
		redirectAttributes.addFlashAttribute("successMessage", "Hai aggiunti una nuova pizza!");
		return "redirect:/pizza";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("pizzaObj", service.getById(id));
		model.addAttribute("ingredienteList", ingredienteService.findAllSortByIngrediente());
		return "/pizza/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("pizza") Pizza formPizza, 
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			model.addAttribute("ingredienteList", ingredienteService.findAllSortByIngrediente());
			return "/pizza/edit";
		}
	
		service.update(formPizza);
		redirectAttributes.addFlashAttribute("successMessage", "Hai modificato una pizza nella lista!");
		return "redirect:/pizza";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		service.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Hai eliminato una pizza dalla lista!");
		return "redirect:/pizza";
	}
}
