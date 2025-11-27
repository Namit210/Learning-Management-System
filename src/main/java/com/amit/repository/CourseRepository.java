package com.amit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.amit.model.Course;

public interface CourseRepository extends MongoRepository<Course, String>{
	
}
