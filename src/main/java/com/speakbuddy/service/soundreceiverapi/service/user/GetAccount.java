package com.speakbuddy.service.soundreceiverapi.service.user;

import com.speakbuddy.service.soundreceiverapi.repository.MasterUserRepositories;
import com.speakbuddy.service.soundreceiverapi.service.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAccount {
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final MasterUserRepositories masterUserRepositories;

    @Autowired
    public GetAccount(MasterUserRepositories masterUserRepositories) {
        this.masterUserRepositories = masterUserRepositories;
    }

    public List<User> findAll(){
        return masterUserRepositories.findAll()
                .stream()
                .map(userMapper::map)
                .toList();
    }
}
