package com.amit.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.amit.model.User;

public interface UserRepository extends MongoRepository<User, String>{

	public Optional<User> findByNickname(String name);
}
