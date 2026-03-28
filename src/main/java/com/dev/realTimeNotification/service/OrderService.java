package com.dev.realTimeNotification.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dev.realTimeNotification.dto.OrderRequestDto;
import com.dev.realTimeNotification.dto.OrderResponseDto;
import com.dev.realTimeNotification.dto.OrderStatusEvent;
import com.dev.realTimeNotification.dto.OrderStatusUpdateRequestDto;
import com.dev.realTimeNotification.exception.OrderNotFoundException;
import com.dev.realTimeNotification.model.Order;

@Service
public class OrderService {

	private final ConcurrentHashMap<String, Order> orderStore = new ConcurrentHashMap<>();
	
	private final OrderStatusProducerService orderStatusProducerService;
	
	public OrderService(OrderStatusProducerService orderStatusProducerService) {
		this.orderStatusProducerService = orderStatusProducerService;
	}
	
	public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
		// TODO Auto-generated method stub
		Order order = new Order();
		order.setOrderId(generateOrderId());
		order.setUserId(orderRequestDto.getUserId());
		order.setItemName(orderRequestDto.getItemName());
		order.setStatus("ORDER_RECEIVED");
		order.setCreatedDate(java.time.LocalDateTime.now());
		orderStore.put(order.getOrderId(), order);
		return mapToResponseDto(order);
	}
	
	@CacheEvict(value = "orders", key = "#orderId")
	public OrderResponseDto updateOrderStatus(String orderId, OrderStatusUpdateRequestDto statusUpdateRequestDto) {
	    Order order = orderStore.get(orderId);
	    if (order == null) {
	        throw new RuntimeException("Order not found: " + orderId);
	    }
        order.setStatus(statusUpdateRequestDto.getStatus());
        OrderStatusEvent orderStatusEvent = new OrderStatusEvent(orderId, order.getUserId(), statusUpdateRequestDto.getStatus());	
        orderStatusProducerService.sendStatusUpdate(orderStatusEvent);
        return mapToResponseDto(order);
	}
	
	@Cacheable(value = "orders", key = "#orderId")
	public OrderResponseDto getOrderById(String orderId) {
        Order order = orderStore.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        return mapToResponseDto(order);
    }
	
	private OrderResponseDto mapToResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUserId());
        dto.setItemName(order.getItemName());
        dto.setStatus(order.getStatus());
        dto.setCreatedDate(order.getCreatedDate());
        return dto;
    }

	private String generateOrderId() {
	    return UUID.randomUUID().toString();
	}

}
