package com.demo.springboot.ordermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.springboot.ordermanagement.dao.ProductDao;
import com.demo.springboot.ordermanagement.model.Product;
import com.demo.springboot.ordermanagement.request.ProductRequest;

@Service // it does business logic
public class ProductService {

	/*
	 * CRUD operations create - createProduct read - findProductById,
	 * findAllProduct update - updateProduct delete - deleteProduct
	 */
	Logger logger = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	private ProductDao productDao;

	public Product createProduct(ProductRequest productRequest) {
		// take all the field form productRequest and set it in product object and
		// save product object into database
		logger.info("createProduct API has started!");
		Product product = new Product();
		product.setProductname(productRequest.getProductname());
		product.setPrice(productRequest.getPrice());
		product.setProductquantity(productRequest.getProductquantity());
		product.setManufacture(productRequest.getManufacture());
		product.setDescription(productRequest.getDescription());
		logger.info("product is saving into database!");
		product = productDao.save(product);
		if(product==null) {
			logger.error("product could not be saved");
			throw new RuntimeException("Unable to save product in database");
		}
		logger.info("product saved and createProduct method ended!");
		return product;

	}

	// Optional<> in springboot will handle the empty vlaue. if value isn't present
	// then it will return null. the return type could
	// just be Product but we want to return null if the product is not present of
	// given id so we are using Optional class instead.

	public Optional<Product> findProductById(Long productId) {

		Optional<Product> product = productDao.findById(productId);
		if(!product.isPresent()) {
			logger.error("product not found");
			throw new RuntimeException("Unable to find product by id");
		}
		logger.info("findProductById serevice class method ended");
		return product;
	}

	public List<Product> findAllProduct() {

		List<Product> productList = productDao.findAll();
		if(productList.isEmpty()) {
			throw new RuntimeException("There are no products in database");
		}
		return productList;
	}

	public void deleteProductById(Long productId) {
		productDao.deleteById(productId);

	}

	// Update Api
	
	public Product updateProduct(Long productId, ProductRequest newProductRequest) {
		
		Product updatedProduct = null;

		Optional<Product> oldProductOptional = productDao.findById(productId);
		if(!oldProductOptional.isPresent()) {
			throw new RuntimeException("Unable to find product with id:	"+productId);
		}
		Product oldProduct = oldProductOptional.get();

		
		oldProduct.setProductname(newProductRequest.getProductname());
		oldProduct.setPrice(newProductRequest.getPrice());
		oldProduct.setProductquantity(newProductRequest.getProductquantity());
		oldProduct.setManufacture(newProductRequest.getManufacture());
		oldProduct.setDescription(newProductRequest.getDescription());
		
		
		updatedProduct = productDao.save(oldProduct);
		if(updatedProduct==null) {
			throw new RuntimeException("Unable to find product with id : "+productId);
		}
		return updatedProduct;

	}
	
	
	public Product updateProductUsingPatch(Long productId, String productquantity) {
		
		
		Product updatedProduct = null;

		Optional<Product> oldProductOptional = productDao.findById(productId);

		Product oldProduct = oldProductOptional.get();

		
		
		oldProduct.setProductquantity(productquantity);
		
		
		updatedProduct = productDao.save(oldProduct);
		
		return updatedProduct;
		
		
	}
	
	/*
	 * when large data are present in database we cannot fetch all of them
	 * we need to fetch only certain number of records and this is done by pagination.
	 * The process of giving page number to each page - Pagination
	 * we need two parameter pageNumber- Number of pages we want
	 * pageSize - how many records should be stored in each page
	 * total number of records = 36
	 * oth page = 1-10
	 * 1st page = 11-20
	 * 2nd page = 21-30
	 * 3rd page = 31-36
	 */
	// sorting- sort the paginated records based on certain fields/column
	// =======================	 	PAGINATION API		===========================================
	
	
	public List<Product> getProductWithPagination(Integer pageNumber, Integer pageSize, String sortColumn) {
		// call findAll method from Product.Dao and pass pageNumber and pageSize as parameter and it reutrns page of Product
		
		// we can use hard codded value of column name like below or just add one parameter in above as String sortColumn and provide the value of it later
		// Page<Product> productPage = productDao.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("productname").ascending() ));
		
		Page<Product> productPage = productDao.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortColumn).ascending() ));
		
		
		// convert the page of product to list of product and return the list 
		List<Product> productList = new ArrayList<>();
		
		for(Product product:productPage) {
			productList.add(product);
			
		}
		return productList;
	}
	
	// ==============================		COUNT PRODUCT API		======================================
	public int countProduct() {
		
		int count = productDao.countProduct();
		return count;
	}

}
