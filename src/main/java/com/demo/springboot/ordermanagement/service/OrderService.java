
package com.demo.springboot.ordermanagement.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.springboot.ordermanagement.dao.CustomerDao;
import com.demo.springboot.ordermanagement.dao.OrderDao;
import com.demo.springboot.ordermanagement.dao.ProductDao;
import com.demo.springboot.ordermanagement.enums.OrderStatus;
import com.demo.springboot.ordermanagement.enums.PaymentMode;
import com.demo.springboot.ordermanagement.model.Customer;
import com.demo.springboot.ordermanagement.model.Order;
import com.demo.springboot.ordermanagement.model.Product;
import com.demo.springboot.ordermanagement.request.OrderRequest;

@Service // it does business logic
public class OrderService {

	/*
	 * CRUD operations create - createOrder read - findOrderById, findAllOrder
	 * update - updateOrder delete - deleteOrder
	 */

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private ProductDao productDao;
	
	// =======================================	SAVE/CREATE/INSERT API	=================================================
	
	public Order createOrder(OrderRequest orderRequest) {
		// take all the field form orderRequest and set it in order object and
		// save order object into database

		Order order = new Order();
		order.setProductPurchaseQuantity(orderRequest.getProductPurchaseQuantity());
		// order.setTotalPrice(orderRequest.getTotalPrice()); // This should not be
		// manually entered rather calculated automatically
		order.setOrderStatus(OrderStatus.IN_PROGRESS.toString()); // by default orderStatus will be inprogress.

		order.setDeliveryAddress(orderRequest.getDeliveryAddress());
		// order.setPaymentMethod(orderRequest.getPaymentMethod());
		// Payment Method we need to set what user sends form the frontend
		
		if (orderRequest.getPaymentMethod().equalsIgnoreCase(PaymentMode.CASH_ON_DELIVERY.toString())) {
			order.setPaymentMethod(PaymentMode.CASH_ON_DELIVERY.toString());
		} else if (orderRequest.getPaymentMethod().equalsIgnoreCase(PaymentMode.DEBIT_CARD.toString())) {
			order.setPaymentMethod(PaymentMode.DEBIT_CARD.toString());
		} else if (orderRequest.getPaymentMethod().equalsIgnoreCase(PaymentMode.CREDIT_CARD.toString())) {
			order.setPaymentMethod(PaymentMode.CREDIT_CARD.toString());
		} else if (orderRequest.getPaymentMethod().equalsIgnoreCase(PaymentMode.UPI.toString())) {
			order.setPaymentMethod(PaymentMode.UPI.toString());
		} else {
			order.setPaymentMethod(PaymentMode.CASH_ON_DELIVERY.toString());
		}
		

		Optional<Customer> cust = customerDao.findById(orderRequest.getCustomerId());
		Optional<Product> prod = productDao.findById(orderRequest.getProductId());

		// get product price from product object and store it in one variable

		String productUnitPrice = prod.get().getPrice();

		// get productPurchaseQuantity and store it in one variable
		String productPurchaseQuantity = orderRequest.getProductPurchaseQuantity();

		// change the datatype to integer and multiply them to get total price
		int totalPrice = Integer.parseInt(productUnitPrice) * Integer.parseInt(productPurchaseQuantity);

		// set the totalPrice to order
		order.setTotalPrice(Integer.toString(totalPrice));

		order.setProduct(prod.get());
		order.setCustomer(cust.get());

		order = orderDao.save(order);

		return order;

	}

	// Optional<> in springboot will handle the empty vlaue. if value isn't present
	// then it will return null. the return type could
	// just be Order but we want to return null if the order is not present of
	// given id so we are using Optional class instead.
	
	
	// ======================================	FIND ORDER BY ID API	======================================================
	public Optional<Order> findOrderById(Long orderId) {

		Optional<Order> order = orderDao.findById(orderId);

		return order;
	}
	// =======================================	FIND ALL ORDER API	===========================================================
	public List<Order> findAllOrder() {

		List<Order> orderList = orderDao.findAll();

		return orderList;
	}
	
	// =======================================	DELETE ORDER BY ID API	=========================================================
	
	public void deleteOrderById(Long orderId) {
		orderDao.deleteById(orderId);

	}

	// =========================================	UPDATE API	================================================================== 

	public Order updateOrder(Long orderId, OrderRequest newOrderRequest) {

		Order updatedOrder = null;

		Optional<Order> oldOrderOptional = orderDao.findById(orderId);

		Order oldOrder = oldOrderOptional.get();

		oldOrder.setProductPurchaseQuantity(newOrderRequest.getProductPurchaseQuantity());
		// oldOrder.setTotalPrice(newOrderRequest.getTotalPrice());

		// oldOrder.setOrderStatus(newOrderRequest.getOrderStatus());

		// get orderStatus form orderRequest and check whether it is equal to
		// orderstatus in enum and set that value

		if (newOrderRequest.getOrderStatus().equalsIgnoreCase(OrderStatus.SHIPPED.toString())) {
			oldOrder.setOrderStatus(OrderStatus.SHIPPED.toString());
		} else if (newOrderRequest.getOrderStatus().equalsIgnoreCase(OrderStatus.DELIVERED.toString())) {
			oldOrder.setOrderStatus(OrderStatus.DELIVERED.toString());
		} else if (newOrderRequest.getOrderStatus().equalsIgnoreCase(OrderStatus.CANCELED.toString())) {
			oldOrder.setOrderStatus(OrderStatus.CANCELED.toString());
		} else if (newOrderRequest.getOrderStatus().equalsIgnoreCase(OrderStatus.REUTRNED.toString())) {
			oldOrder.setOrderStatus(OrderStatus.REUTRNED.toString());
		} else {
			oldOrder.setOrderStatus(OrderStatus.IN_PROGRESS.toString());
		}

		oldOrder.setDeliveryAddress(newOrderRequest.getDeliveryAddress());
		// oldOrder.setPaymentMethod(newOrderRequest.getPaymentMethod()); // payment
		// method should only be choosen from frontend

		if (newOrderRequest.getPaymentMethod().equalsIgnoreCase(PaymentMode.CASH_ON_DELIVERY.toString())) {
			oldOrder.setPaymentMethod(PaymentMode.CASH_ON_DELIVERY.toString());
		} else if (newOrderRequest.getPaymentMethod().equalsIgnoreCase(PaymentMode.DEBIT_CARD.toString())) {
			oldOrder.setPaymentMethod(PaymentMode.DEBIT_CARD.toString());
		} else if (newOrderRequest.getPaymentMethod().equalsIgnoreCase(PaymentMode.CREDIT_CARD.toString())) {
			oldOrder.setPaymentMethod(PaymentMode.CREDIT_CARD.toString());
		} else if (newOrderRequest.getPaymentMethod().equalsIgnoreCase(PaymentMode.UPI.toString())) {
			oldOrder.setPaymentMethod(PaymentMode.UPI.toString());
		} else {
			oldOrder.setPaymentMethod(PaymentMode.CASH_ON_DELIVERY.toString());
		}

		Optional<Customer> cust = customerDao.findById(newOrderRequest.getCustomerId());
		Optional<Product> prod = productDao.findById(newOrderRequest.getProductId());

		// get product price from product object and store it in one variable

		String productUnitPrice = prod.get().getPrice();

		// get productPurchaseQuantity and store it in one variable
		String productPurchaseQuantity = newOrderRequest.getProductPurchaseQuantity();

		// change the datatype to integer and multiply them to get total price
		int totalPrice = Integer.parseInt(productUnitPrice) * Integer.parseInt(productPurchaseQuantity);

		// set the totalPrice to order
		oldOrder.setTotalPrice(Integer.toString(totalPrice));
		oldOrder.setProduct(prod.get());
		oldOrder.setCustomer(cust.get());

		updatedOrder = orderDao.save(oldOrder);

		return updatedOrder;

	}

	public Order updateOrderUsingPatch(Long orderId, String productPurchaseQuantity) {

		Order updatedOrder = null;

		Optional<Order> oldOrderOptional = orderDao.findById(orderId);

		Order oldOrder = oldOrderOptional.get();

		oldOrder.setProductPurchaseQuantity(productPurchaseQuantity);

		updatedOrder = orderDao.save(oldOrder);

		return updatedOrder;

	}

	public int countOrder() {

		int count = orderDao.countOrder();
		return count;
	}

}



