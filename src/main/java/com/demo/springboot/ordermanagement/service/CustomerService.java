package com.demo.springboot.ordermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springboot.ordermanagement.dao.CustomerDao;
import com.demo.springboot.ordermanagement.model.Customer;
import com.demo.springboot.ordermanagement.request.CustomerRequest;

@Service // it does business logic
public class CustomerService {

	/*
	 * CRUD operations create - createCustomer read - findCustomerById,
	 * findAllCustomer update - updateCustomer delete - deleteCustomer
	 */

	@Autowired
	private CustomerDao customerDao;

	public Customer createCustomer(CustomerRequest customerRequest) {
		// take all the field form customerRequest and set it in customer object and
		// save customer object into database

		Customer customer = new Customer();
		customer.setCustomerName(customerRequest.getCustomerName());
		customer.setEmail(customerRequest.getEmail());
		customer.setMobile(customerRequest.getMobile());
		customer.setAddress(customerRequest.getAddress());

		customer = customerDao.save(customer);

		return customer;

	}

	// Optional<> in springboot will handle the empty vlaue. if value isn't present
	// then it will return null. the return type could
	// just be Customer but we want to return null if the customer is not present of
	// given id so we are using Optional class instead.

	public Optional<Customer> findCustomerById(Long customerId) {

		Optional<Customer> customer = customerDao.findById(customerId);

		return customer;
	}

	public List<Customer> findAllCustomer() {

		List<Customer> customerList = customerDao.findAll();

		return customerList;
	}

	public void deleteCustomerById(Long customerId) {
		customerDao.deleteById(customerId);

	}

	// Update Api
	
	public Customer updateCustomer(Long customerId, CustomerRequest newCustomerRequest) {
		
		Customer updatedCustomer = null;

		Optional<Customer> oldCustomerOptional = customerDao.findById(customerId);

		Customer oldCustomer = oldCustomerOptional.get();

		
		oldCustomer.setCustomerName(newCustomerRequest.getCustomerName());
		oldCustomer.setEmail(newCustomerRequest.getEmail());
		oldCustomer.setMobile(newCustomerRequest.getMobile());
		oldCustomer.setAddress(newCustomerRequest.getAddress());
		
		
		updatedCustomer = customerDao.save(oldCustomer);
		
		return updatedCustomer;

	}
	
	
	public Customer updateCustomerUsingPatch(Long customerId, String address) {
		
		
		Customer updatedCustomer = null;

		Optional<Customer> oldCustomerOptional = customerDao.findById(customerId);

		Customer oldCustomer = oldCustomerOptional.get();

		
		
		oldCustomer.setAddress(address);
		
		
		updatedCustomer = customerDao.save(oldCustomer);
		
		return updatedCustomer;
		
	}
	
	public int countCustomer() {
		
		int count = customerDao.countCustomer();
		return count;
	}

}
