package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.RoleRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.RoleResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
