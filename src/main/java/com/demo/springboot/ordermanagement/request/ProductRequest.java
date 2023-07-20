package com.demo.springboot.ordermanagement.request;

import lombok.Data;

@Data
public class ProductRequest {
	private String productname;

	private String price;

	private String productquantity;

	private String manufacture;

	private String description;

}
