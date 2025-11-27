package com.amit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amit.model.Course;

import com.amit.service.CourseService;

@RestController
public class CourseController {
	
	@Autowired
	CourseService cService;
	
	@GetMapping("/courses")
	public List<Course> getAllCoursesHandler(){
		
		return cService.getAllCourses();
	}

	@PostMapping("/courses")
	@PreAuthorize("hasRole('ADMIN')")
	public Course postCourseHandler(@RequestBody Course course){
		
		return cService.saveCourse(course);
	}
	
	@PutMapping("/courses")
	@PreAuthorize("hasRole('ADMIN')")
	public Course updateCourseHandler(@RequestBody Course course) {
		return cService.updateCourse(course);
	}

	@DeleteMapping("/courses")
	@PreAuthorize("hasRole('ADMIN')")
	public Course deleteCourseHandler(@RequestParam("cid") String cid) {
		return cService.deleteCourse(cid);
	}
	
	@GetMapping("/courses/modules")
	public List<String> getAllModulesHandler(@RequestParam("cid") String cid){
		
		return cService.getAllModules(cid);
	}

	@PostMapping("/courses/modules")
	@PreAuthorize("hasRole('ADMIN')")
	public Course inserModuleHandler(@RequestParam("cid") String cid ,@RequestBody String moduleName){
		
		return cService.insertModule(cid,moduleName);
	}
	
	@PutMapping("/courses/modules/{mno}")
	@PreAuthorize("hasRole('ADMIN')")
	public Course updateModuleNameHandlere(@RequestParam("cid") String cid,
			@PathVariable("mno") Integer moduleNo,
			@RequestBody String moduleName) {
		System.out.println("Module no: "+moduleNo);
		return cService.updateModuleName(cid, moduleNo, moduleName);
	}

	@DeleteMapping("/courses/modules/{mno}")
	@PreAuthorize("hasRole('ADMIN')")
	public Course deleteModule(@RequestParam("cid") String cid,@PathVariable("mno") Integer moduleNo) {
		return cService.deleteModule(cid,moduleNo);
	}
	
}
