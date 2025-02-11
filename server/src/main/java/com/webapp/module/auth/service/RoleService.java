package com.webapp.module.auth.service;

import com.webapp.module.auth.dto.request.RoleRequest;
import com.webapp.module.auth.dto.response.RoleResponse;
import com.webapp.model.Role;
import com.webapp.module.auth.mapper.RoleMapper;
import com.webapp.module.auth.repository.PermissionRepository;
import com.webapp.module.auth.repository.RoleRepository;
import java.util.HashSet;
import java.util.List;

import jakarta.transaction.Transactional;
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

  @Transactional
  public RoleResponse create(RoleRequest request) {
    var role = roleMapper.toRole(request);
    var permissions = permissionRepo.findAllById(request.getPermissions());
    role.setPermissions(new HashSet<>(permissions));
    roleRepo.save(role);
    return roleMapper.toRoleResponse(role);
  }

  @Transactional
  public synchronized RoleResponse updateRole(String role, RoleRequest request) {
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
