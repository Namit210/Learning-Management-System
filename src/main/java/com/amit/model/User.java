package com.amit.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {
	
	@Id
	private String nickname;
	private String name;
	private Integer age;
	private String phone;
	private String address;
	private String password;
	private Set<String> roles = new HashSet<>();
	private Map<String, Double> enrollment = new HashMap<String, Double>();
	private Map<Integer, String> assignment = new HashMap<Integer, String>();
	
	// NEW FIELD: Admin secret key for registration (not stored in database)
	@Transient
	private String adminSecretKey;
	
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Map<String, Double> getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(Map<String, Double> enrollment) {
		this.enrollment = enrollment;
	}
	public Map<Integer, String> getAssignment() {
		return assignment;
	}
	public void setAssignment(Map<Integer, String> assignment) {
		this.assignment = assignment;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	// NEW GETTER AND SETTER for adminSecretKey
	public String getAdminSecretKey() {
		return adminSecretKey;
	}
	public void setAdminSecretKey(String adminSecretKey) {
		this.adminSecretKey = adminSecretKey;
	}
	
	public User(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public User() {
		
	}
}
