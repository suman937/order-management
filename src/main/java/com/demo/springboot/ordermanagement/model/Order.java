package com.demo.springboot.ordermanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor

public class Order {
	@Id
	@Column(name = "order_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderid;

	@Column(name = "product_purchase_quantity", nullable = false)
	private String productPurchaseQuantity;

	@Column(name = "total_price", nullable = false)
	private String totalPrice;

	@Column(name = "order_status", nullable = false)
	private String orderStatus;

	@Column(name = "delivery_address", nullable = false)
	private String deliveryAddress;

	@Column(name = "payment_method", nullable = false)
	private String paymentMethod;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

}
