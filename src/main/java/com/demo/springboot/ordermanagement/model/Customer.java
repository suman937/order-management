package com.demo.springboot.ordermanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="customer")
@AllArgsConstructor	//all parameterized field constructor
@NoArgsConstructor	//no field constructor or default constructor

public class Customer {
	@Id // refers that this is the primary key of the table
	@Column(name="customer_id", nullable= false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerid;
	
	@Column(name="customer_name", nullable= false)
	private String customerName;
	
	@Column(name="email", unique=true)
	private String email;
	
	@Column(name="mobile", nullable= false)
	private String mobile;
	
	@Column(name="address", nullable= false)
	private String address;

}
