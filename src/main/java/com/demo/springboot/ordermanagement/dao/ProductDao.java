package com.demo.springboot.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.springboot.ordermanagement.model.Product;

public interface ProductDao extends JpaRepository<Product, Long>{
	
	
@Query(nativeQuery = true, value="Select count(*) from product")
	
	public int countProduct();

}
