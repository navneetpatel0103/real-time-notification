package com.dev.realTimeNotification.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dev.realTimeNotification.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	
	private final NotificationService notificationService;
	
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@GetMapping("/subscribe/{userId}")
	public SseEmitter subscribeToNotifications(@PathVariable String userId) throws IOException {
		// Logic to create and return an SseEmitter for the client to subscribe to notifications
		return notificationService.subscribe(userId);
	}

}
