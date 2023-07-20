package com.demo.springboot.ordermanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springboot.ordermanagement.model.Customer;
import com.demo.springboot.ordermanagement.request.CustomerRequest;
import com.demo.springboot.ordermanagement.service.CustomerService;

@RestController
@RequestMapping(value = "/om/v1/customer")	// it maps the web request url
public class CustomerController {
	/*
	 * postman
	 * dispatcher servlets
	 * controller class
	 * service class
	 * dao
	 */
	/*
	 * create/insert/save   - post
	 * update		   		- put/patch
	 * delete		   		- delete
	 * read/retrieve   		- get
	 */
	
	
	@Autowired
	private CustomerService customerService;
	
	
	//@RequestBody takes input from front end in json format and passes it to api.
	
	@PostMapping(value = "/save") // Other way - @RequestMapping(value = "/save", method= RequestMethod.POST)
	public Customer saveCustomer(@RequestBody CustomerRequest customerRequest) {
		Customer customer = customerService.createCustomer(customerRequest);
		
		return customer;
		
	}
	//either could work perfect
//	public ResponseEntity<?> saveCustomer(@RequestBody CustomerRequest customerRequest){
//		Customer customer  = customerService.createCustomer(customerRequest);
//		
//		return ResponseEntity.ok().body(customer);
//	}
	
	@GetMapping(value="/find/{customerId}")
	
	public ResponseEntity<?> getCustomerById(@PathVariable("customerId") Long customerId) {
		
		Optional<Customer> customer = customerService.findCustomerById(customerId);
		
		return ResponseEntity.ok().body(customer);
		
		
	}
	
	
	// if we are returning all or if one customerById then the return type must be ResponseEntirty<?> 
	// also the value fetch form the Dao class should be palced into List if we are returning all customer. if we are only 
	// returning one customerById then this has to be handled with Optionl<Customer> just to handle the null value or just 
	// Customer if you don't want to handle the null vlaue.
	
	@GetMapping(value = "/findAll")
	public ResponseEntity<?>  getAllCustomer() {
		
		List<Customer> custList = customerService.findAllCustomer();
		
		return ResponseEntity.ok().body(custList);
	}
	
	@DeleteMapping(value = "/delete/{customerId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") Long customerId) {
		
		customerService.deleteCustomerById(customerId);
		
		return ResponseEntity.ok().body("The customerId " + customerId + " got deleted.");
		
	}
	
	
	// Update customer values
	@PutMapping(value="/update/{customerId}")
	public ResponseEntity<?> updateOldCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerRequest customerRequest) {
		
		Customer updatedCust = customerService.updateCustomer(customerId, customerRequest);
		
		return ResponseEntity.ok().body(updatedCust);
		
	}
	
	
	@PatchMapping(value="/updatepatch/{customerId}")
	public ResponseEntity<?> updateOldCustomerUsingPatch(@PathVariable("customerId") Long customerId,@RequestParam("address") String address) {
		
		Customer updatedCust = customerService.updateCustomerUsingPatch(customerId, address);
		
		return ResponseEntity.ok().body(updatedCust);
		
	}
	
	@GetMapping(value="/count")
	public ResponseEntity<?> customerCount() {
		int count = customerService.countCustomer();
		
		return ResponseEntity.ok().body("The total number of customer is: "+count);
		
	}

}
