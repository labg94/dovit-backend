package com.dovit.backend.util;

import com.dovit.backend.domain.User;
import com.dovit.backend.model.requests.UserRequest;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public class ModelMapper {

    public static void mapUserRequestToUser(UserRequest request, User user){
        user.setId(request.getId());
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setActive(request.getActive());
    }

}
