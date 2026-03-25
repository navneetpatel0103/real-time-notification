package com.dev.realTimeNotification.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.dev.realTimeNotification.dto.OrderStatusEvent;

@Service
public class OrderStatusProducerService {
	
	private final KafkaTemplate<String, OrderStatusEvent> kafkaTemplate;
	
	public OrderStatusProducerService(KafkaTemplate<String, OrderStatusEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	private static final String TOPIC = "order-status-updates";
	
	// Publishing the order status update event to Kafka topic
	public void sendStatusUpdate(OrderStatusEvent event) {
		kafkaTemplate.send(TOPIC, event);
	}

}
