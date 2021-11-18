package com.betamart.module.role.controller;

import com.betamart.base.constant.CommonMessage;
import com.betamart.base.payload.response.BaseResponse;
import com.betamart.base.util.JwtUtil;
import com.betamart.model.Role;
import com.betamart.module.role.payload.request.RoleListRequest;
import com.betamart.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public Object addRole(@RequestBody RoleListRequest roleListRequest) {
        try {
            AtomicBoolean doesExistAll = new AtomicBoolean(true);
            roleListRequest.getRoleNameList().forEach(name ->{
                if(!roleRepository.existsByName(name)){
                    roleRepository.save(new Role(name));
                    doesExistAll.set(false);
                }
            });
            if(doesExistAll.get()){
                return new BaseResponse<>(CommonMessage.NOT_SAVED);
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
            return new BaseResponse<>(CommonMessage.NOT_SAVED);
        }
        return new BaseResponse<>(CommonMessage.SAVED, "Success");
    }

    @PostMapping("/update/{id}/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object updateRole(@PathVariable("id") Long id, @PathVariable("name") String name, @RequestHeader("Authorization") String token) {
        try {
           String username = jwtUtil.extractUsername(token.substring(7));
           Role role = roleRepository.findById(id).get();
           role.setName(name);
           role.setUpdatedBy(username);
           role.setUpdatedDate(new Date());

           roleRepository.save(role);
        } catch (Exception e) {
            System.out.println("error = " + e);
            return new BaseResponse<>(CommonMessage.NOT_UPDATED);
        }
        return new BaseResponse<>(CommonMessage.UPDATED, "Success");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object deleteRole(@PathVariable("id") Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("error = " + e);
            return new BaseResponse<>(CommonMessage.NOT_DELETED);
        }
        return new BaseResponse<>(CommonMessage.DELETED, "Success");
    }
}
