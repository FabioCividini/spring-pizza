package org.generation.italy.service;

import java.util.List;

import org.generation.italy.model.Ingrediente;
import org.generation.italy.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {

	@Autowired
	private IngredienteRepository repository;
	
	public List<Ingrediente> findAllSortByIngrediente(){
		return repository.findAll(Sort.by("ingrediente"));
	}
	
	public List<Ingrediente> findByKeywordSortedByRecent(String keyword){
		return repository.findByIngredienteContainingIgnoreCaseOrderByIngrediente(keyword);
	}
	
	public Ingrediente create(Ingrediente ingrediente) {
		return repository.save(ingrediente);
	}
	
	public Ingrediente getById(Integer id) {
		return repository.getById(id);
	}
	
	public Ingrediente update(Ingrediente ingrediente) {
		return repository.save(ingrediente);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
	
}
