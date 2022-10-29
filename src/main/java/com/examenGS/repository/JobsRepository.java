package com.examenGS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.examenGS.entity.Jobs;

public interface JobsRepository extends CrudRepository<Jobs, Long> {
	
	List<Jobs> findAll();
	
	Optional<Jobs> findById(Long id);

}
