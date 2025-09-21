package com.spring.sakura.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.sakura.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	List<Address> findByUserIdOrderByIsPrimaryDesc(Long userId);

	// Add this one for all addresses of a user
    List<Address> findByUserId(Long userId);
    
    long countByUserId(Long userId);

}
