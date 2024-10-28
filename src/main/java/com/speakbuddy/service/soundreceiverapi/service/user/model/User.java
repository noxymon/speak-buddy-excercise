package com.speakbuddy.service.soundreceiverapi.service.user.model;

import lombok.Data;

import java.time.Instant;

@Data
public class User {
    private Integer id;
    private String fullName;
    private String email;
    private Instant registeredAt;
    private Instant updatedAt;
    private Boolean isActive = false;
}
