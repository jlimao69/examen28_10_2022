package com.examenGS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.examenGS.entity.Employees;

public interface EmployeesRepository extends CrudRepository<Employees, Integer>{
	
	 List<Employees> findAll();
	 
	 List<Employees> findByEmployeeId(Integer employeeId);
	 
	 List<Employees> findByNameAndLastName(String name, String lastname);
	 
	 @Query("SELECT max(u) FROM Employees u  ")
	 Employees findMax();

}
