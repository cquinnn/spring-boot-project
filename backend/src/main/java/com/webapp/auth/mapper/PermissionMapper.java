package com.webapp.auth.mapper;

import com.webapp.auth.dto.request.PermissionRequest;
import com.webapp.auth.dto.response.PermissionResponse;
import com.webapp.auth.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
  Permission toPermission(PermissionRequest request);

  PermissionResponse toPermissionResponse(Permission permission);
}
