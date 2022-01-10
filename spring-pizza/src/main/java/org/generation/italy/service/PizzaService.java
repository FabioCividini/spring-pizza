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
	
	public Pizza save(Pizza pizza) {
		return repo.save(pizza);
	}
}
