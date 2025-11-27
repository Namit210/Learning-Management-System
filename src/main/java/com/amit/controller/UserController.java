package com.amit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.amit.exceptions.UserException;
import com.amit.model.User;
import com.amit.service.UserService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

	@Autowired
	private UserService uService;
	
	
	@PostMapping("/join")
	public User saveUserHandler(@RequestBody User user) {
		return uService.saveUser(user);
	}
	
	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> findAllUsersHandler(){
		return uService.findAllUsers();
	}
	
	@GetMapping("/find/{nickname}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getUserHandler(@PathVariable("nickname") String nickname) throws UserException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserNickname = authentication.getName();
		
		// Users can only access their own data, unless they're admin
		if (!currentUserNickname.equals(nickname) && 
		    !authentication.getAuthorities().stream()
		        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
			    .body("You can only access your own user data");
		}
		
		return ResponseEntity.ok(uService.getUserByNickname(nickname));
	}
	
	@PutMapping("/enroll")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> enrollCourse(@RequestParam("cid") String cid, @RequestBody String nickname) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserNickname = authentication.getName();
		
		// Users can only enroll themselves, unless they're admin
		if (!currentUserNickname.equals(nickname) && 
		    !authentication.getAuthorities().stream()
		        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
			    .body("You can only enroll yourself in courses");
		}
		
		return ResponseEntity.ok(uService.enrollCourse(cid, nickname));
	}
	
	@GetMapping("/getcourses")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getEnrolledCoursesHandler(@RequestParam("name") String name){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserNickname = authentication.getName();
		
		// Users can only see their own enrolled courses, unless they're admin
		if (!currentUserNickname.equals(name) && 
		    !authentication.getAuthorities().stream()
		        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
			    .body("You can only view your own enrolled courses");
		}
		
		return ResponseEntity.ok(uService.getEnrolledCourses(name));
	}
}
