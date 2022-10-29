package com.examenGS.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examenGS.dto.Request1_1;
import com.examenGS.dto.Request1_4;
import com.examenGS.entity.Employees;
import com.examenGS.services.IEmployeesService;

@RestController
@RequestMapping("/employee/v1")
public class EmployeesController {
	
	
	private IEmployeesService iEmployeesService;
	
	public EmployeesController(IEmployeesService iEmployeesService) {
		this.iEmployeesService = iEmployeesService;
	}
	
	
	@PutMapping("/newEmployee")
	public ResponseEntity<?> newEmployee (@RequestBody Request1_1 body){
		
		Map<String, String> respuesta = new HashMap<>();
		respuesta = iEmployeesService.enviaDiag(body);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
		
	}
	
	@GetMapping("/jobsEmploy/{jobId}")
	public ResponseEntity<?> jobEmployee (@PathVariable("jobId") Integer jobId){
		
		List<Employees> respuesta = new ArrayList();
		respuesta = iEmployeesService.employeeByJob(jobId);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
		
	}
	
	@GetMapping("/jobsEmployThread")
	public ResponseEntity<?> jobEmployeeHilo (@RequestBody Request1_4 body){
		
		Map<String, Object> respuesta = new HashMap<>(); 
		respuesta = iEmployeesService.employeeByJobHilo(body.getEmployeeId());
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
		
	}

}
