package com.webapp.identity.mapper;

import com.webapp.identity.dto.request.RoleRequest;
import com.webapp.identity.dto.response.RoleResponse;
import com.webapp.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  @Mapping(target = "permissions", ignore = true)
  Role toRole(RoleRequest request);

  RoleResponse toRoleResponse(Role role);
}
