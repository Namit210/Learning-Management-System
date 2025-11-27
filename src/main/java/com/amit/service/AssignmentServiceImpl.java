package com.amit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.exceptions.AssignmentException;

import com.amit.model.Assignment;

import com.amit.repository.AssignmentRepository;

@Service
public class AssignmentServiceImpl implements AssignmentService{

	@Autowired
	private AssignmentRepository arepo;
	
	@Override
	public List<Assignment> getAllAssignments() {
	return arepo.findAll();	
	}

	@Override
	public Assignment getAssignmentById(String id) throws AssignmentException {
		Optional<Assignment> opt = arepo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		else
			throw new AssignmentException("Assignment not found");
	}

	@Override
	public Assignment postAssignment(Assignment assignment) {
		return arepo.save(assignment);
	}

	@Override
	public Assignment deleteAssignment(String id) {
		Optional<Assignment> opt = arepo.findById(id);
		 if(opt.isPresent()) {
			 arepo.delete(opt.get());
			 return opt.get();
		 }
		 else
			 throw new AssignmentException("Assignment index is invalid");
	}

	@Override
	public Assignment updateAssignment(Assignment assignment) throws AssignmentException {
		Optional<Assignment> opt = arepo.findById(assignment.getId());
		if(opt.isPresent()) {
			arepo.delete(opt.get());
			return arepo.save(assignment);
		}
		else
			throw new AssignmentException("Error updating Assignment!");
		
	}


}
