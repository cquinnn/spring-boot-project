package com.webapp.identity.mapper;

import com.webapp.identity.dto.request.PermissionRequest;
import com.webapp.identity.dto.response.PermissionResponse;
import com.webapp.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
  Permission toPermission(PermissionRequest request);

  PermissionResponse toPermissionResponse(Permission permission);
}
