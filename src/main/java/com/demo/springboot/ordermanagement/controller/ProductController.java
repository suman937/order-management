package com.demo.springboot.ordermanagement.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.demo.springboot.ordermanagement.model.Product;
import com.demo.springboot.ordermanagement.request.ProductRequest;
import com.demo.springboot.ordermanagement.service.ProductService;

@RestController
@RequestMapping(value = "/om/v1/product") // it maps the web request url
public class ProductController {
	/*
	 * postman dispatcher servlets controller class service class dao
	 */
	/*
	 * create/insert/save - post update - put/patch delete - delete read/retrieve -
	 * get
	 */

	// Logger - keep track of application
	// 3 levels of logger: Info, debug, error
	// logger.Info - this is normally goes inside try block
	// logger.error - this is normally goes inside catch block
	// logger.debug - same as info but while running in debug mode it will show debug message instead of info message
	//
	Logger logger = LoggerFactory.getLogger(ProductController.class); // always use Logger of slf4j package
	@Autowired
	private ProductService productService;

	// @RequestBody takes input from front end in json format and passes it to api.

	@PostMapping(value = "/save") // Other way - @RequestMapping(value = "/save", method= RequestMethod.POST)
	public ResponseEntity<?> saveProduct(@RequestBody ProductRequest productRequest) {
		try {
			logger.info("saveProduct API has started!");

			Product product = productService.createProduct(productRequest);
			logger.info("saveProduct API has ended!");

			return ResponseEntity.ok().body(product);
		} catch (Exception e) {
			logger.error("Exception occur while saving the product");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	// either could work perfect
//	public ResponseEntity<?> saveProduct(@RequestBody ProductRequest productRequest){
//		Product product  = productService.createProduct(productRequest);
//		
//		return ResponseEntity.ok().body(product);
//	}

	@GetMapping(value = "/find/{productId}")

	public ResponseEntity<?> getProductById(@PathVariable("productId") Long productId) {
		try {
			logger.info("getProductById API has started!");
			Optional<Product> product = productService.findProductById(productId);
			logger.info("getProductById API has ended!");

			return ResponseEntity.ok().body(product);
		} catch (Exception e) {
			logger.error("Product of given id is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	// if we are returning all or if one productById then the return type must be
	// ResponseEntirty<?>
	// also the value fetch form the Dao class should be palced into List if we are
	// returning all product. if we are only
	// returning one productById then this has to be handled with Optionl<Product>
	// just to handle the null value or just
	// Product if you don't want to handle the null vlaue.

	@GetMapping(value = "/findAll")
	public ResponseEntity<?> getAllProduct() {
		try {
			List<Product> custList = productService.findAllProduct();

			return ResponseEntity.ok().body(custList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping(value = "/delete/{productId}")
	public ResponseEntity<?> deleteProductById(@PathVariable("productId") Long productId) {
		try {
			productService.deleteProductById(productId);

			return ResponseEntity.ok().body("The productId " + productId + " got deleted.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	// Update product values
	@PutMapping(value = "/update/{productId}")
	public ResponseEntity<?> updateOldProduct(@PathVariable("productId") Long productId,
			@RequestBody ProductRequest productRequest) {
		try {
			Product updatedCust = productService.updateProduct(productId, productRequest);

			return ResponseEntity.ok().body(updatedCust);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@PatchMapping(value = "/updatepatch/{productId}")
	public ResponseEntity<?> updateOldProductUsingPatch(@PathVariable("productId") Long productId,
			@RequestParam("address") String address) {
		try {
			Product updatedCust = productService.updateProductUsingPatch(productId, address);

			return ResponseEntity.ok().body(updatedCust);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> productCount() {
		int count = productService.countProduct();

		return ResponseEntity.ok().body("The total number of product is: " + count);

	}

	// pagination can be use to any class customer and order class as well but for
	// now we are just using it to product
	@GetMapping(value = "/withpage")
	public ResponseEntity<?> getAllProductWithPagination(@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("pageSize") Integer pageSize, String sortColumn) {
		List<Product> prodList = productService.getProductWithPagination(pageNumber, pageSize, sortColumn);
		return ResponseEntity.ok().body(prodList);
	}

}
