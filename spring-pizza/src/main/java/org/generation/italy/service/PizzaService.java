package org.generation.italy.service;

import java.util.List;


import org.generation.italy.model.Pizza;
import org.generation.italy.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository repo;
	
	public List<Pizza> findAllSortedByNome(){
		return repo.findAll(Sort.by("nome"));
	}
	
	public List<Pizza> findByKeywordSortedByRecent(String keyword){
		return repo.findByNomeContainingIgnoreCaseOrderByNome(keyword);
	}
	
	public Pizza create(Pizza pizza) {
		return repo.save(pizza);
	}
	
	public Pizza getById(Integer id) {
		return repo.getById(id);
	}
	
	public Pizza update(Pizza pizza) {
		return repo.save(pizza);
	}
	
	public void deleteById(Integer id) {
		repo.deleteById(id);
	}
}
