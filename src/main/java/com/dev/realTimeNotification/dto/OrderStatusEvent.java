package com.dev.realTimeNotification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusEvent {
	// class to represent the order status update event that will be sent to Kafka
	private String orderId;
	private String userId;
	private String status;
	
}
