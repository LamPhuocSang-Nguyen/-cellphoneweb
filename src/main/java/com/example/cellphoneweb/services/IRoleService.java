package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.RoleDTO;
import com.example.cellphoneweb.models.RoleEntity;

import java.util.List;

public interface IRoleService {
    RoleEntity createRole(RoleDTO roleDTO);
    RoleEntity updateRole(long role_Id, RoleDTO roleDTO);
    RoleEntity deleteRole(long role_Id);
    RoleEntity getRole(long role_Id);
    boolean existsByName(String name);
    List<RoleEntity> getAllRoles();
    List<RoleEntity> getRolesByName(String name);
}
