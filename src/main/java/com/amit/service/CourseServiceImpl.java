package com.amit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.exceptions.CourseException;
import com.amit.model.Course;
import com.amit.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseRepository crepo;

	@Override
	public Course saveCourse(Course course) {
		
		return crepo.save(course);
	}

	@Override
	public List<Course> getAllCourses() {

		return crepo.findAll();
	}

	@Override
	public Course updateCourse(Course course) throws CourseException {
		Optional<Course> courseOpt = crepo.findById(course.getId());
		if(courseOpt.isPresent()) {
			return crepo.save(course);
		}
		else
			throw new CourseException("Course not found!");
		
	}

	@Override
	public Course deleteCourse(String cid) throws CourseException{
		Optional<Course> courseOpt = crepo.findById(cid);
		if(courseOpt.isPresent()) {
			crepo.delete(courseOpt.get());
			return courseOpt.get();
		}
		else 
			throw new CourseException("Course not found");

	}

	@Override
	public Course insertModule(String cid, String moduleName) {
		Optional<Course> courseOpt = crepo.findById(cid);
		if(courseOpt.isPresent()) {
			Course course = courseOpt.get();
			course.getModules().add(moduleName);
			return crepo.save(course);
		}
		else
			throw new CourseException("Course not found!");
	}

	@Override
	public List<String> getAllModules(String cid)throws CourseException {
		Optional<Course> courseOpt = crepo.findById(cid);
		if(courseOpt.isPresent()) {
			return courseOpt.get().getModules();
		}
		else
			throw new CourseException("Course not found!");
	}

	@Override
	public Course updateModuleName(String cid, Integer moduleNo, String moduleName) throws CourseException{
		Optional<Course> courseOpt = crepo.findById(cid);
		if(courseOpt.isPresent()) {
			Course course = courseOpt.get();
			List<String> modules = course.getModules();
			if(moduleNo <=0 || moduleNo >= modules.size()+1)
				throw new CourseException("Invalid Module Number");
			else {
				modules.set(moduleNo-1, moduleName);
				course.setModules(modules);
				return crepo.save(course);
			}
		}
		else
			throw new CourseException("Course not found!");
	}

	@Override
	public Course deleteModule(String cid, Integer moduleNo)throws CourseException {
		Optional<Course> courseOpt = crepo.findById(cid);
		if(courseOpt.isPresent()) {
			Course course = courseOpt.get();
			List<String> modules = course.getModules();
			if(moduleNo <0 || moduleNo >= modules.size()+1)
				throw new CourseException("Invalid Module Number");
			else {
				modules.remove(moduleNo-1);
				course.setModules(modules);
				return crepo.save(course);
			}
		}
		else
			throw new CourseException("Course not found!");
	}
}
