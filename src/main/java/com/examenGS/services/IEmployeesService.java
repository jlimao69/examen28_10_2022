package com.examenGS.services;

import java.util.List;
import java.util.Map;

import com.examenGS.dto.Request1_1;
import com.examenGS.dto.Request1_4;
import com.examenGS.entity.Employees;

public interface IEmployeesService {

	Map<String, String> enviaDiag(Request1_1 request1_1);
	List<Employees> employeeByJob(Integer jobId);
	 Map<String, Object> employeeByJobHilo(List<Integer> numEmployee);
}
