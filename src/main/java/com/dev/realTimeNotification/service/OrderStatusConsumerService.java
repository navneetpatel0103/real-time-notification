package com.dev.realTimeNotification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.dev.realTimeNotification.dto.OrderStatusEvent;

@Service
public class OrderStatusConsumerService {

	private final NotificationService notificationService;
	
	public OrderStatusConsumerService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@KafkaListener(topics = "order-status-updates", groupId = "notification-group")
	public void consumeStatusUpdate(OrderStatusEvent event) {
		 // Logic to consume the order status update event from Kafka and send notifications to users
		String userId = event.getUserId();
		String status = event.getStatus();
		notificationService.sendNotification(userId,status);
	}
	
}
