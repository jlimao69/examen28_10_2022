package com.examenGS.dto;

public class Request1_1 {

	private int genderId;
	private int jobId;
	private String name;
	private String lastName;
	private String birthdate;

	public Request1_1() {
		
	}
	
	public Request1_1(int genderId, int jobId, String name, String lastName, String birthdate) {
		
		this.genderId = genderId;
		this.jobId = jobId;
		this.name = name;
		this.lastName = lastName;
		this.birthdate = birthdate;
	}
	
	
	public int getGenderId() {
		return genderId;
	}
	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	
	
	
}
