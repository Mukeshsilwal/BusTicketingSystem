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
    public RoleDto createRole(RoleDto roleDto, int id) {
        Role role=this.dtoToRole(roleDto);
        User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        role.setUser(user);
        Role role1=this.roleRepo.save(role);
        return toDto(role1);
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto, int id) {
        Role role=this.dtoToRole(roleDto);
        User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setUser(user);
        Role role1=this.roleRepo.save(role);
        return toDto(role1);
    }

    @Override
    public List<RoleDto> getAllRole() {
        List<Role> roles=this.roleRepo.findAll();
        return roles.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(int id) {
        Role role=this.roleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Role","id",id));
        return toDto(role);
    }

    @Override
    public void deleteRole(int id) {
        Role role=this.roleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Role","id",id));
        this.roleRepo.delete(role);

    }
    public Role dtoToRole(RoleDto roleDto){
        return this.modelMapper.map(roleDto, Role.class);
    }
    public RoleDto toDto(Role role){
        return this.modelMapper.map(role, RoleDto.class);
    }
}
