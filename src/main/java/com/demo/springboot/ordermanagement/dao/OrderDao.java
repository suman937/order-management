package com.demo.springboot.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.springboot.ordermanagement.model.Order;

public interface OrderDao extends JpaRepository<Order, Long>{
	
@Query(nativeQuery = true, value="Select count(*) from order")
	
	public int countOrder();

}
