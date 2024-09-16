package com.example.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practice.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

}
