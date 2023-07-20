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

import com.demo.springboot.ordermanagement.model.Order;
import com.demo.springboot.ordermanagement.request.OrderRequest;
import com.demo.springboot.ordermanagement.service.OrderService;

@RestController
@RequestMapping(value = "/om/v1/order")	// it maps the web request url
public class OrderController {
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
	private OrderService orderService;
	
	
	//@RequestBody takes input from front end in json format and passes it to api.
	
	@PostMapping(value = "/save") // Other way - @RequestMapping(value = "/save", method= RequestMethod.POST)
	public Order saveOrder(@RequestBody OrderRequest orderRequest) {
		Order order = orderService.createOrder(orderRequest);
		
		return order;
		
	}
	//either could work perfect
//	public ResponseEntity<?> saveOrder(@RequestBody OrderRequest orderRequest){
//		Order order  = orderService.createOrder(orderRequest);
//		
//		return ResponseEntity.ok().body(order);
//	}
	
	@GetMapping(value="/find/{orderId}")
	
	public ResponseEntity<?> getOrderById(@PathVariable("orderId") Long orderId) {
		
		Optional<Order> order = orderService.findOrderById(orderId);
		
		return ResponseEntity.ok().body(order);
		
		
	}
	
	
	// if we are returning all or if one orderById then the return type must be ResponseEntirty<?> 
	// also the value fetch form the Dao class should be palced into List if we are returning all order. if we are only 
	// returning one orderById then this has to be handled with Optionl<Order> just to handle the null value or just 
	// Order if you don't want to handle the null vlaue.
	
	@GetMapping(value = "/findAll")
	public ResponseEntity<?>  getAllOrder() {
		
		List<Order> custList = orderService.findAllOrder();
		
		return ResponseEntity.ok().body(custList);
	}
	
	@DeleteMapping(value = "/delete/{orderId}")
	public ResponseEntity<?> deleteOrderById(@PathVariable("orderId") Long orderId) {
		
		orderService.deleteOrderById(orderId);
		
		return ResponseEntity.ok().body("The orderId " + orderId + " got deleted.");
		
	}
	
	
	// Update order values
	@PutMapping(value="/update/{orderId}")
	public ResponseEntity<?> updateOldOrder(@PathVariable("orderId") Long orderId, @RequestBody OrderRequest orderRequest) {
		
		Order updatedCust = orderService.updateOrder(orderId, orderRequest);
		
		return ResponseEntity.ok().body(updatedCust);
		
	}
	
	
	@PatchMapping(value="/updatepatch/{orderId}")
	public ResponseEntity<?> updateOldOrderUsingPatch(@PathVariable("orderId") Long orderId,@RequestParam("address") String address) {
		
		Order updatedCust = orderService.updateOrderUsingPatch(orderId, address);
		
		return ResponseEntity.ok().body(updatedCust);
		
	}
	
	@GetMapping(value="/count")
	public ResponseEntity<?> orderCount() {
		int count = orderService.countOrder();
		
		return ResponseEntity.ok().body("The total number of order is: "+count);
		
	}

}


