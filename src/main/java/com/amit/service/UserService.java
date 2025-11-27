package com.amit.service;

import com.amit.exceptions.CourseException;
import com.amit.exceptions.UserException;
import com.amit.model.Course;
import com.amit.model.User;
import java.util.List;

public interface UserService {

	public User saveUser(User user);
	public User getUserByNickname(String num) throws UserException;
	public List<User> findAllUsers();
	public List<Course> getEnrolledCourses(String user) throws UserException;
	public User enrollCourse(String cid, String username) throws UserException, CourseException;
}
