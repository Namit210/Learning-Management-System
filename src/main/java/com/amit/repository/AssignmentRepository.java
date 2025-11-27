package com.amit.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.amit.model.Assignment;

public interface AssignmentRepository extends MongoRepository<Assignment,String>{
	
}
