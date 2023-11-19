package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.RoleDto;
import com.Transaction.transaction.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<RoleDto>> getAllRole(){
        List<RoleDto> roleDtos=this.roleService.getAllRole();
        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Integer id){
        RoleDto roleDto=this.roleService.getRoleByUserId(id);
        return new ResponseEntity<>(roleDto,HttpStatus.OK);
    }
    @PostMapping("/user/{id}")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto,@PathVariable Integer id){
        RoleDto roleDto1=this.roleService.createRole(roleDto,id);
        return new ResponseEntity<>(roleDto1,HttpStatus.CREATED);
    }
    @PutMapping("/role/{id}/user/{uId}")
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto,@PathVariable Integer id,@PathVariable Integer uId){
        RoleDto roleDto1=this.roleService.updateRole(roleDto,id,uId);
        return new ResponseEntity<>(roleDto1,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Integer id){
        this.roleService.deleteRole(id);
        return new ResponseEntity<>(new ApiResponse("Role Deleted",true,HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        RoleDto roleDto1=this.roleService.createRole(roleDto);
        return new ResponseEntity<>(roleDto1,HttpStatus.CREATED);
    }

}
