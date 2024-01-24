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
    private final RoleService  roleService;

    @GetMapping("/get")
    public ResponseEntity<List<RoleDto>> getAllRole(){
        List<RoleDto> roleDto=this.roleService.getAllRole();
        return new ResponseEntity<>(roleDto, HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<RoleDto> getById(@PathVariable int id){
        RoleDto roleDto=this.roleService.getRoleById(id);
        return new ResponseEntity<>(roleDto,HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto,@PathVariable int id){
        RoleDto roleDto1=this.roleService.updateRole(roleDto,id);
        return new ResponseEntity<>(roleDto1,HttpStatus.OK);
    }
    @PostMapping("/post/{id}")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto,@PathVariable int id){
        RoleDto roleDto1=this.roleService.createRole(roleDto,id);
        return new ResponseEntity<>(roleDto1,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable int id){
        this.roleService.deleteRole(id);
        return new ResponseEntity<>(new ApiResponse("Role has been deleted",true,HttpStatus.OK),HttpStatus.OK);
    }
}
