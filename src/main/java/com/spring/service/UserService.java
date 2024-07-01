package com.spring.service;

import com.spring.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwtToken) throws Exception;
    public User findUserByEmail(String email) throws Exception;

}
