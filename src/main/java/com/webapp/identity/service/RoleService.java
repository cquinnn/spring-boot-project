package com.webapp.identity.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.identity.dto.request.RoleRequest;
import com.webapp.identity.dto.response.RoleResponse;
import com.webapp.identity.mapper.RoleMapper;
import com.webapp.identity.repository.PermissionRepository;
import com.webapp.identity.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
	RoleRepository roleRepo;
	RoleMapper roleMapper;
	PermissionRepository permissionRepo;
	
	public RoleResponse create (RoleRequest request) {
		var role = roleMapper.toRole(request);
		var permissions = permissionRepo.findAllById(request.getPermissions());
		role.setPermissions(new HashSet<>(permissions));
		roleRepo.save(role);
		return roleMapper.toRoleResponse(role);
	}
	
	public List<RoleResponse> getAll(){
		return roleRepo.findAll()
				.stream()
				.map(roleMapper::toRoleResponse)
				.toList();
		
	}
	
	public void delete(String role) {
		roleRepo.deleteById(role);
	}
	
}