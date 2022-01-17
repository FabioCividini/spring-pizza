package org.generation.italy.repository;

import java.util.List;

import org.generation.italy.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {

	List<Ingrediente> findByIngredienteContainingIgnoreCaseOrderByIngrediente(String keyword);
}
