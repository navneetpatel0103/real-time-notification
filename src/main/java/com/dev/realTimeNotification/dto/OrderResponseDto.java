package com.dev.realTimeNotification.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

	 private String orderId;
	 private String userId;
	 private String itemName;
	 private String status;
	 private LocalDateTime createdDate;
	
}
