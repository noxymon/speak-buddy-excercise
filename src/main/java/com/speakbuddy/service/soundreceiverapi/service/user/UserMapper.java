package com.speakbuddy.service.soundreceiverapi.service.user;

import com.speakbuddy.service.soundreceiverapi.repository.models.MasterUser;
import com.speakbuddy.service.soundreceiverapi.service.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * Mapper interface for converting between MasterUser and User objects.
 */
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Maps a MasterUser object to a User object.
     *
     * @param masterUser the MasterUser object to map
     * @return the mapped User object
     */
    User map(MasterUser masterUser);

    /**
     * Maps a list of MasterUser objects to a list of User objects.
     *
     * @param masterUserList the list of MasterUser objects to map
     * @return the list of mapped User objects
     */
    List<User> map(List<MasterUser> masterUserList);
}