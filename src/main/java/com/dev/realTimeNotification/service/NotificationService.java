package com.dev.realTimeNotification.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class NotificationService {
	
	private final ConcurrentHashMap<String, SseEmitter> userEmittersMap = new ConcurrentHashMap<>();
	
	// Method to subscribe a user to notifications
	public SseEmitter subscribe(String userId) throws IOException {
		
		SseEmitter existingEmitter = userEmittersMap.get(userId);
        if (existingEmitter != null) {
            existingEmitter.complete();
        }
		
		SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30 minutes
		emitter.onCompletion(() -> userEmittersMap.remove(userId));
		emitter.onTimeout(() -> userEmittersMap.remove(userId));
		emitter.onError((e) -> userEmittersMap.remove(userId));
		userEmittersMap.put(userId, emitter);
		emitter.send(SseEmitter.event().name("INIT").data("Subscribed to notifications"));
		return emitter;
	}

	public void sendNotification(String userId, String status) {
		SseEmitter emitter = userEmittersMap.get(userId);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().name("ORDER_STATUS_UPDATE").data(status));
			} catch (IOException e) {
				//If sending fails, the emitter is dead — remove it from the map immediately.
				userEmittersMap.remove(userId);
			}
		}
		
	}
	
}
