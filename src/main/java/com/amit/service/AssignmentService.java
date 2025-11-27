package com.amit.service;

import java.util.List;

import com.amit.exceptions.AssignmentException;
import com.amit.model.Assignment;

public interface AssignmentService {
	
	public List<Assignment> getAllAssignments();
	
	public Assignment getAssignmentById(String id) throws AssignmentException;
	
	public Assignment postAssignment(Assignment assignment);
	
	public Assignment deleteAssignment(String id);
	
	public Assignment updateAssignment(Assignment assignment) throws AssignmentException;

	
}
