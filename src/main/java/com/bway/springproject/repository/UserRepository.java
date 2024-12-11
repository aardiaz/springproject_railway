package com.bway.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bway.springproject.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsernameAndPassword(String un, String psw);
	
	//@Query("FROM User where  username = :un and password = :psw ")
	@Query(value = "select * from user_tbl  where  username= :un and password = :psw ", nativeQuery = true)
	User userLogin(String un, String psw);
	
}
