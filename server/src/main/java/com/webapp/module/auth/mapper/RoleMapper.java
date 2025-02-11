package com.webapp.module.auth.mapper;

import com.webapp.module.auth.dto.request.RoleRequest;
import com.webapp.module.auth.dto.response.RoleResponse;
import com.webapp.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

  @Mapping(target = "permissions", ignore = true)
  Role toRole(RoleRequest request);

  RoleResponse toRoleResponse(Role role);

  @Mapping(target = "permissions", ignore = true)
  @Mapping(target = "name", ignore = true)
  void updateRole(@MappingTarget Role role_update, RoleRequest request);
}
