package com.speakbuddy.service.soundreceiverapi.controller;


import com.speakbuddy.service.soundreceiverapi.service.user.GetAccount;
import com.speakbuddy.service.soundreceiverapi.service.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class UserController {

    private final GetAccount getAccount;

    @Autowired
    public UserController(GetAccount getAccount) {
        this.getAccount = getAccount;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> all = getAccount.findAll();
        return ResponseEntity
                .ok()
                .body(all);
    }
}
