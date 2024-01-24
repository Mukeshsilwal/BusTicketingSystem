package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto,int id);
    RoleDto updateRole(RoleDto roleDto,int id);
    List<RoleDto> getAllRole();
    RoleDto getRoleById(int id);
    void deleteRole(int id);
}
