package com.example.practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.practice.dto.request.RoleRequest;
import com.example.practice.dto.response.RoleResponse;
import com.example.practice.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	@Mapping(target="permissions", ignore = true)
	Role toRole(RoleRequest request);
	RoleResponse toRoleResponse(Role role);
}
