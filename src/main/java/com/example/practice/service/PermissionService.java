package com.example.practice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.practice.dto.request.PermissionRequest;
import com.example.practice.dto.response.PermissionResponse;
import com.example.practice.entity.Permission;
import com.example.practice.mapper.PermissionMapper;
import com.example.practice.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
	PermissionRepository permissionRepo;
	PermissionMapper permissionMapper;
	
	public PermissionResponse create(PermissionRequest request) {
		Permission permission = permissionMapper.toPermission(request);
		permission = permissionRepo.save(permission);
		return permissionMapper.toPermissionResponse(permission);
	}
	
	public List<PermissionResponse> getAll(){
		var permissions = permissionRepo.findAll();
		return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
	}
	
	public void delete(String permission) {
		permissionRepo.deleteById(permission);
	}
}