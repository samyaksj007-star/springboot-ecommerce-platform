package com.spring.sakura.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.spring.sakura.entities.Users;

public interface LoginRepository extends CrudRepository<Users, Long>{
	
	
	public Users findByEmail(String email);
	
	@Query(value="select * from users where id = :id", nativeQuery = true)
	public Users getUserProfileData(@Param("id") Long id);
	

}
