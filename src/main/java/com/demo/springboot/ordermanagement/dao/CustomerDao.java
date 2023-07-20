package com.demo.springboot.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.springboot.ordermanagement.model.Customer;

@Repository 	// it does database operation. since it is repository layer we need to give @Repository annotation.
// JpaRepository has in built method for database operation. we are inheriting it and using it in our application.
public interface CustomerDao extends JpaRepository<Customer, Long>{
	
	
	// @Query is used to write user defined query. 
	// nativeQuery = true tells us that we have our own query to run
	// value will be the query statement
	
	@Query(nativeQuery = true, value="Select count(*) from customer")
	
	public int countCustomer();
	

}
