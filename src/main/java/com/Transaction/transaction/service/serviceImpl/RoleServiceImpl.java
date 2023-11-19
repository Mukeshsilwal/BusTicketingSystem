package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.Role;
import com.Transaction.transaction.entity.User;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.RoleDto;
import com.Transaction.transaction.repository.RoleRepo;
import com.Transaction.transaction.repository.UserRepo;
import com.Transaction.transaction.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    @Override
    public RoleDto createRole(RoleDto roleDto,int id) {
        User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        Role role=this.dtoToRole(roleDto);
        role.setUser(user);
        Role role1=this.roleRepo.save(role);
        return roleToDto(role1);
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto, int id, int uId) {
        Role role=this.roleRepo.findById(id).orElseThrow();
        User user=this.userRepo.findById(uId).orElseThrow();
        role.setUser(user);
        Role role1=this.roleRepo.save(role);
        return roleToDto(role1);
    }

    @Override
    public void deleteRole(int id) {
        Role role=this.roleRepo.findById(id).orElseThrow();
        this.roleRepo.delete(role);

    }

    @Override
    public RoleDto getRoleByUserId(int id) {
        Role role=this.roleRepo.findById(id).orElseThrow();
        return roleToDto(role);
    }

    @Override
    public List<RoleDto> getAllRole() {
        List<Role> roles=this.roleRepo.findAll();
        return roles.stream().map(this::roleToDto).collect(Collectors.toList());
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role=this.dtoToRole(roleDto);
        Role role1=this.roleRepo.save(role);
        return roleToDto(role1);
    }

    private Role dtoToRole(RoleDto roleDto){
        return this.modelMapper.map(roleDto, Role.class);
    }
    private RoleDto roleToDto(Role role){
        return this.modelMapper.map(role,RoleDto.class);
    }

}
