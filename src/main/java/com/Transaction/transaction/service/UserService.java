package com.Transaction.transaction.service;

import com.Transaction.transaction.model.ChangePasswordRequest;
import com.Transaction.transaction.model.User;
import com.Transaction.transaction.payloads.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    void deleteUser(Integer id);

    UserDto updateUser(UserDto userDto, Integer id);

    UserDto getUserById(Integer id);

    List<UserDto> getAllUser();

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void sentOtp(User username) throws JsonProcessingException;

}
