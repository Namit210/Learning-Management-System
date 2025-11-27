package com.amit.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Courses")
public class Course {
	
	@Id
	private String id;
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

//	private Integer index;
	private String name;
	private double duration;
	private String desc;
	private String live;
	private List<String> modules = new ArrayList<String>();
	
	public Course() {
		
	}

	
//	public Integer getIndex() {
//		return index;
//	}
//
//
//	public void setIndex(Integer index) {
//		this.index = index;
//	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLive() {
		return live;
	}

	public void setLive(String live) {
		this.live = live;
	}

	public List<String> getModules() {
		return modules;
	}

	public void setModules(List<String> modules) {
		this.modules = modules;
	}
	
		
	
}
