package com.webapp.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.identity.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

}
