package com.amit.service;

import java.util.List;


import com.amit.exceptions.CourseException;
import com.amit.model.Course;


public interface CourseService {

	public Course saveCourse(Course course);
	public List<Course> getAllCourses();
	public Course updateCourse(Course course) throws CourseException;
	public Course deleteCourse(String cid) throws CourseException;
	
	public Course insertModule(String cid, String moduleName)throws CourseException;
	public List<String> getAllModules(String cid)throws CourseException;
	public Course updateModuleName(String cid, Integer moduleNo, String moduleName)throws CourseException;
	public Course deleteModule(String cid, Integer moduleNo)throws CourseException;
	
}
