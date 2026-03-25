package com.dev.realTimeNotification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String orderId;
    private String userId;
    private String itemName;
    private String status;
    private LocalDateTime createdDate;
}