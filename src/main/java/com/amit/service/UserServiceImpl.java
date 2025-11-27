package com.amit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.exceptions.CourseException;
import com.amit.exceptions.UserException;
import com.amit.model.Course;
import com.amit.model.User;
import com.amit.repository.CourseRepository;
import com.amit.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private CourseRepository crepo;
	
	@Override
	public User saveUser(User user) {
		return urepo.save(user);
	}

	@Override
	public User getUserByNickname(String nickname) throws UserException {
		Optional<User> userOpt =  urepo.findByNickname(nickname);
		
		if(userOpt.isPresent()) {
			return userOpt.get();
		}
		else 
			throw new UserException("User not Found with name"+nickname);
		
	}

	@Override
	public List<User> findAllUsers() {
		return urepo.findAll();
	}

	@Override
	public List<Course> getEnrolledCourses(String nickname) {
		
		Optional<User> userOpt =  urepo.findByNickname(nickname);
		if(userOpt.isPresent()) {
		
		 Set<String> cids =  userOpt.get().getEnrollment().keySet();
		 List<Course> enrolledCourses = new ArrayList<Course>();
		 for(String cid : cids) {
			 Optional<Course> courseOpt =  crepo.findById(cid);
			 courseOpt.ifPresent(enrolledCourses::add);
		 }
		
		return enrolledCourses;
		}
		else
			throw new UserException("User not found!");
		
		}

	@Override
	public User enrollCourse(String cid, String nickname) throws UserException, CourseException {
		Optional<User> userOpt= urepo.findByNickname(nickname);
		if(userOpt.isEmpty())
			throw new UserException("User does not exist!");
		else if(crepo.findById(cid).isEmpty())
			throw new CourseException("Course does not exist!");
		
		else {
			User user = userOpt.get();
		
		user.getEnrollment().put(cid,0.0);
		return urepo.save(user);
	
		}
		}



	
	
	

}
