package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.Ticket;
import com.Transaction.transaction.entity.User;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.RoleDto;
import com.Transaction.transaction.payloads.TicketDto;
import com.Transaction.transaction.payloads.UserDto;
import com.Transaction.transaction.repository.TicketRepo;
import com.Transaction.transaction.repository.UserRepo;
import com.Transaction.transaction.service.TicketService;
import com.Transaction.transaction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final TicketService ticketService;
    private final TicketRepo ticketRepo;
//    private final RoleRepo roleRepo;
//    private final RoleService roleService;


   @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {

        User user=this.dtoToUser(userDto);
        User user1=this.userRepo.save(user);
        return userToDto(user1);
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
    public TicketDto ticketToDto(Ticket ticket){
        return this.modelMapper.map(ticket, TicketDto.class);
    }
//    private RoleDto roleToDto(Role role){
//        return this.modelMapper.map(role,RoleDto.class);
//    }

}
