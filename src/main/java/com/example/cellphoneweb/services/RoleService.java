package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.RoleDTO;
import com.example.cellphoneweb.models.RoleEntity;
import com.example.cellphoneweb.repositorise.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    public final RoleRepository roleRepository;

    @Override
    public RoleEntity createRole(RoleDTO roleDTO) {
        RoleEntity role = RoleEntity.builder()
                .name(roleDTO.getName().toUpperCase())
                .build();
        return roleRepository.save(role);
    }

    @Override
    public RoleEntity updateRole(long role_Id, RoleDTO roleDTO) {
        RoleEntity role = roleRepository.findById(role_Id).orElse(null);
        if(role != null){
            role.setName(roleDTO.getName().toUpperCase());
            return roleRepository.save(role);
        }
        return null;
    }

    @Override
    public RoleEntity deleteRole(long role_Id) {
        RoleEntity role = roleRepository.findById(role_Id).orElse(null);
        if(role != null){
            roleRepository.delete(role);
            return role;
        }
        return null;
    }

    @Override
    public RoleEntity getRole(long role_Id) {
        return roleRepository.findById(role_Id).orElse(null);
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<RoleEntity> getRolesByName(String name) {
        return roleRepository.findByName(name);
    }
}
