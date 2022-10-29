package com.examenGS.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.examenGS.entity.Employees;
import com.examenGS.repository.EmployeesRepository;

public class EmployeeRunnable implements Runnable{

	@Autowired
	private EmployeesRepository employeesRepository;
	
	private Employees employees;
	private int idEm;
	
	
	public EmployeeRunnable(Employees employees, int idEm) {
		this.employees = employees;
		this.idEm = idEm;
	}

	@Override
	public void run() {

		Optional<Employees> list = employeesRepository.findById(idEm);
		 this.esperarXsegundos(2);
	}
		
	private void esperarXsegundos(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
