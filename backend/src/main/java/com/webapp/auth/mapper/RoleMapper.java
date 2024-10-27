package com.webapp.auth.mapper;

import com.webapp.auth.dto.request.RoleRequest;
import com.webapp.auth.dto.response.RoleResponse;
import com.webapp.auth.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  @Mapping(target = "permissions", ignore = true)
  Role toRole(RoleRequest request);

  RoleResponse toRoleResponse(Role role);

  @Mapping(target = "permissions", ignore = true)
  @Mapping(target = "name", ignore = true)
  void updateRole(@MappingTarget Role role_update, RoleRequest request);
}
