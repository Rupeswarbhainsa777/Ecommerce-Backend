package com.rrecom.ecomsoft.service;

import com.rrecom.ecomsoft.io.UserRequest;
import com.rrecom.ecomsoft.io.UserResponse;

import java.util.List;

public interface UserService
{

        UserResponse createUser(UserRequest request);

        String getUserRole(String email);

        List<UserResponse> readUsers();

        void deleteUser(String id);

}
