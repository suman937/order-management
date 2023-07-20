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
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	@Column(name="product_id", nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productid;
	
	@Column(name="ProductName", nullable=false)
	private String productname;
	
	@Column(name="Price", nullable=false)
	private String price;
	
	@Column(name="ProductQuantity", nullable=false)
	private String productquantity;
	
	@Column(name="Manufacture", nullable=false)
	private String manufacture;
	
	@Column(name="Description", nullable=false)
	private String description;
	

}
