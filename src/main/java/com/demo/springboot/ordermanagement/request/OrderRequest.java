package com.demo.springboot.ordermanagement.request;

import lombok.Data;

@Data
public class OrderRequest {

	private String productPurchaseQuantity;

	private String totalPrice;

	private String orderStatus;

	private String deliveryAddress;

	private String paymentMethod;

	private Long customerId;

	private Long productId;

}

