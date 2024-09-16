package com.example.practice.mapper;

import org.mapstruct.Mapper;

import com.example.practice.dto.request.PermissionRequest;
import com.example.practice.dto.response.PermissionResponse;
import com.example.practice.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
	Permission toPermission(PermissionRequest request);
	PermissionResponse toPermissionResponse(Permission permission);
}