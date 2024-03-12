package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.UserDto;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    void deleteUser(Integer id);
    UserDto updateUser(UserDto userDto,Integer id);
    UserDto getUserById(Integer id);
    List<UserDto> getAllUser();
}
