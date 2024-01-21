package com.Transaction.transaction.service.serviceImpl;


import com.Transaction.transaction.entity.User;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.exception.UserAlreadyExistsException;
import com.Transaction.transaction.payloads.UserDto;
import com.Transaction.transaction.repository.UserRepo;
import com.Transaction.transaction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


   @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {

       User user=this.dtoToUser(userDto);
       if(!userRepo.existsByEmail(user.getEmail())){
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       User user1=this.userRepo.save(user);
       return userToDto(user1);}
       else{
           throw new UserAlreadyExistsException("User Already Registered !");
       }
    }

    @Override
    public void deleteUser(Integer id) {
        User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        this.userRepo.delete(user);

    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User user1=this.userRepo.save(user);
        return userToDto(user1);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users=this.userRepo.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }



    @Override
    public boolean isUserAvailable(String email) {

        return true;
    }

    public User dtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto,User.class);
    }
    private UserDto userToDto(User user){
        return this.modelMapper.map(user, UserDto.class);
    }

}
