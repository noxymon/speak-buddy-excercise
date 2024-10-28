package com.speakbuddy.service.soundreceiverapi.service.user;

import com.speakbuddy.service.soundreceiverapi.repository.models.MasterUser;
import com.speakbuddy.service.soundreceiverapi.service.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    User map(MasterUser masterUser);
    List<User> map(List<MasterUser> masterUserList);
}
