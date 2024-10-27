package com.webapp.auth.service;

import com.webapp.auth.dto.request.RoleRequest;
import com.webapp.auth.dto.response.RoleResponse;
import com.webapp.auth.entity.Role;
import com.webapp.auth.mapper.RoleMapper;
import com.webapp.auth.repository.PermissionRepository;
import com.webapp.auth.repository.RoleRepository;
import java.util.HashSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
  RoleRepository roleRepo;
  RoleMapper roleMapper;
  PermissionRepository permissionRepo;

  public RoleResponse create(RoleRequest request) {
    var role = roleMapper.toRole(request);
    var permissions = permissionRepo.findAllById(request.getPermissions());
    role.setPermissions(new HashSet<>(permissions));
    roleRepo.save(role);
    return roleMapper.toRoleResponse(role);
  }

  public RoleResponse updateRole(String role, RoleRequest request) {
    Role role_update =
        roleRepo.findByName(role).orElseThrow(() -> new RuntimeException("Role not found"));

    roleMapper.updateRole(role_update, request);
    var permissions = permissionRepo.findAllById(request.getPermissions());
    role_update.setPermissions(new HashSet<>(permissions));

    return roleMapper.toRoleResponse(roleRepo.save(role_update));
  }

  public List<RoleResponse> getAll() {
    return roleRepo.findAll().stream().map(roleMapper::toRoleResponse).toList();
  }

  public void delete(String role) {
    roleRepo.deleteById(role);
  }
}
