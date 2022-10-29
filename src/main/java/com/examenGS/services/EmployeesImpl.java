package com.examenGS.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;

import com.examenGS.dto.Request1_1;
import com.examenGS.entity.Employees;
import com.examenGS.entity.Genders;
import com.examenGS.entity.Jobs;
import com.examenGS.repository.EmployeesRepository;
import com.examenGS.repository.GendersRepository;
import com.examenGS.repository.JobsRepository;

@Service
public class EmployeesImpl implements IEmployeesService {
	


	private EmployeesRepository employeesRepository;
	private JobsRepository jobsRepository;
	private GendersRepository gendersRepository;
	
	
	
	public EmployeesImpl(  EmployeesRepository employeesRepository,
			JobsRepository jobsRepository,
			GendersRepository gendersRepository) {
		this.employeesRepository = employeesRepository;
		this.jobsRepository = jobsRepository;
		this.gendersRepository = gendersRepository;
	}

	@Override
	public Map<String, String> enviaDiag(Request1_1 request1_1) {

		Map<String, String> confirmacion = new HashMap<>();
		Optional<Jobs> jobs = jobsRepository.findById(new Long(request1_1.getJobId()));
		Optional<Genders> genders = gendersRepository.findById(new Long(request1_1.getGenderId()));
		
		if (jobs.isPresent() == false || genders.isPresent() == false){
			confirmacion.put("id", null);
			confirmacion.put("success", "false");
			return confirmacion;
		}
		
		List<Employees> respEmployeesList = employeesRepository.findByNameAndLastName(request1_1.getName().toUpperCase(), request1_1.getLastName().toUpperCase());
		
		if(!respEmployeesList.isEmpty()) {
			confirmacion.put("id", null);
			confirmacion.put("success", "false");
			return confirmacion;
		}
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);

			Date date = formatter.parse(request1_1.getBirthdate());
			
		     Duration duration = Duration.between(date.toInstant(), Instant.now());
		     long yearsOld = duration.toDays()/ 365;
	        
			if(yearsOld < 18) {
				confirmacion.put("id", null);
				confirmacion.put("success", "false");
				return confirmacion;
			}
		     
			
			Employees employees = new Employees();
			employees.setName(request1_1.getName().toUpperCase());
			employees.setLastName(request1_1.getLastName().toUpperCase());
			employees.setJobs(jobs.get());
			employees.setGenders(genders.get());
			employees.setBirthdate(date);
			
			Employees employeesMax = new Employees();
			employeesMax = employeesRepository.findMax();
			employees.setEmployeeId(employeesMax.getEmployeeId() + 1);
			
			Employees newEmployees =  employeesRepository.save(employees);
			
			confirmacion.put("id", newEmployees.getEmployeeId().toString());
			confirmacion.put("success", "true");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			confirmacion.put("id", null);
			confirmacion.put("success", "false");
		} 
		
		return confirmacion;
		
	}


	@Override
	public List<Employees> employeeByJob(Integer jobId) {
		List<Employees> list = new ArrayList<>();
		List<Employees> listfinal = new ArrayList<>();
		list = employeesRepository.findAll();
		
		//filtrar por puesto
		List<Employees> listFilter = list.stream().filter(n -> n.getJobs().getJobsId() == 1).collect(Collectors.toList());			
		
		// ordenar por apellido
		Collections.sort(listFilter, 
		(x, y) -> x.getLastName().compareToIgnoreCase(y.getLastName()));
		
		// agrupar por apellido
		Map<String, List<Employees>> mapGroup = listFilter.stream().collect(Collectors.groupingBy(n -> n.getLastName()));			
		
		List<List<Employees>> lists = new ArrayList<>(mapGroup.values());
		
		for (List<Employees> list2 : lists) {
			listfinal.addAll(list2);
		}
		
		return listFilter;
	}

	@Override
	public Map<String, Object> employeeByJobHilo(List<Integer> numEmployee) {
		
		Map<String, Object> confirmacion = new HashMap<>();
		List<Employees> listEmpl = new ArrayList<>();
		Employees employeesRun = new Employees();
		
		if(numEmployee.isEmpty()) {
			confirmacion.put("employees", null);
			confirmacion.put("success", "false");
			return confirmacion;
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		for(int i = 0; i<= numEmployee.size(); i++) { 
			Runnable empleado = new EmployeeRunnable(employeesRun, numEmployee.get(i));
			executor.execute(empleado);
		}
		
		 executor.shutdown();	
		
        while (!executor.isTerminated()) {
        	// Espero a que terminen de ejecutarse todos los procesos 
        	// para pasar a las siguientes instrucciones 
        	
        	listEmpl.add(employeesRun);
        }
    
		
		return confirmacion;
	}

}
