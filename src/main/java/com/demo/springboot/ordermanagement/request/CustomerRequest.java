package com.demo.springboot.ordermanagement.request;

import lombok.Data;

@Data
public class CustomerRequest {

	private String customerName;

	private String email;

	private String mobile;

	private String address;

}
