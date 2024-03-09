package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.UserDto;
import com.Transaction.transaction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id){
        UserDto userDto=this.userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDto=this.userService.getAllUser();
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable Integer id){
        UserDto userDto1=this.userService.updateUser(userDto,id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){

        this.userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse("User has been deleted",true,HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Validated @RequestBody UserDto userDto){
        UserDto userDto1=this.userService.createUser(userDto);
        return new ResponseEntity<>(userDto1,HttpStatus.CREATED);
    }
}
