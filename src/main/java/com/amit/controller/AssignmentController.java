package com.amit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.amit.model.Assignment;
import com.amit.service.AssignmentService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/assignments")
public class AssignmentController {

	@Autowired
	private AssignmentService aService;
	
	@GetMapping("/")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<Assignment> getAllAssignments(){
		return aService.getAllAssignments();
	}
	
	@GetMapping("/{index}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Assignment getAssignmentById(@PathVariable("assgnid") String id) {
		return aService.getAssignmentById(id);
	}
	
	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public Assignment postAssignmentHandler(@RequestBody Assignment assignment) {
	    return aService.postAssignment(assignment);
	}
	
	@DeleteMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public Assignment deleteAssignmentHandler(@RequestParam("id") String assgnid ) {
	    return aService.deleteAssignment(assgnid);
	}
	
	@PutMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public Assignment updateAssignmentHandler(@RequestBody Assignment assignment) {
		return aService.updateAssignment(assignment);
	}
}
