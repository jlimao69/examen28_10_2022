package com.examenGS.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.examenGS.entity.Genders;

public interface GendersRepository extends CrudRepository<Genders, Long> {

	 List<Genders> findAll();
}
