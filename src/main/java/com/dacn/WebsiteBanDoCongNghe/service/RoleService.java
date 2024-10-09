package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.RoleRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.RoleResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Role;
import com.dacn.WebsiteBanDoCongNghe.mapper.RoleMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.RoleReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleReponsitory roleReponsitory;
    RoleMapper roleMapper;

//    Create role
    public RoleResponse createdRole(RoleRequest request){
        Role role = roleMapper.toRole(request);
        return roleMapper.toRoleResponse(roleReponsitory.save(role));
    }

//    Get all role
    public List<RoleResponse> getAll(){
        return roleReponsitory.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

//    Delete role
    public void deleteRole(String role){
        roleReponsitory.deleteById(role);
    }
}
