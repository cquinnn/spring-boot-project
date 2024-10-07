package com.webapp.identity.mapper;

import org.mapstruct.Mapper;

import com.webapp.identity.dto.request.PermissionRequest;
import com.webapp.identity.dto.response.PermissionResponse;
import com.webapp.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
	Permission toPermission(PermissionRequest request);
	PermissionResponse toPermissionResponse(Permission permission);
}