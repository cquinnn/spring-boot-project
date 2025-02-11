package com.webapp.module.auth.mapper;

import com.webapp.module.auth.dto.request.PermissionRequest;
import com.webapp.module.auth.dto.response.PermissionResponse;
import com.webapp.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
  Permission toPermission(PermissionRequest request);

  PermissionResponse toPermissionResponse(Permission permission);
}
