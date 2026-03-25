package com.dev.realTimeNotification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.realTimeNotification.dto.OrderRequestDto;
import com.dev.realTimeNotification.dto.OrderResponseDto;
import com.dev.realTimeNotification.dto.OrderStatusUpdateRequestDto;
import com.dev.realTimeNotification.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
		// Logic to create an order and return the response
		return ResponseEntity.ok(orderService.createOrder(orderRequestDto));
	}
	
	@PatchMapping("/{orderId}/status")
	public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable String orderId, @RequestBody OrderStatusUpdateRequestDto status) {
		// Logic to update the order status and return the response
		return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
	}
	
}
