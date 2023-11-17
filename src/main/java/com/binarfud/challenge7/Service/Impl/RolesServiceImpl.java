package com.binarfud.challenge7.Service.Impl;

import com.binarfud.challenge7.Model.Roles;
import com.binarfud.challenge7.Repository.RoleRepository;
import com.binarfud.challenge7.Service.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Roles> getAllRoles() {
        try {
            List<Roles> roles = roleRepository.findAll();
            return roles;
        } catch (Exception e) {
            log.error("Error while getting all roles: " + e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve roles.", e);
        }
    }

    @Override
    public Optional<Roles> getRoleById(Integer id) {
        try {
            Optional<Roles> role = roleRepository.findById(Long.valueOf(id));
            return role;
        } catch (Exception e) {
            log.error("Error while getting role by ID: " + e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve role by ID.", e);
        }
    }

    @Override
    public boolean addNewRole(Roles role) {
        try {
            Roles savedRole = roleRepository.save(role);
            return savedRole != null;
        } catch (Exception e) {
            log.error("Error while adding a new role: " + e.getMessage(), e);
            throw new RuntimeException("Failed to add a new role.", e);
        }
    }

    @Override
    public void updateRole(Roles role) {
        try {
            roleRepository.save(role);
        } catch (Exception e) {
            log.error("Error while updating role: " + e.getMessage(), e);
            throw new RuntimeException("Failed to update role.", e);
        }
    }

    @Override
    public void deleteRoleById(Integer id) {
        try {
            roleRepository.deleteById(Long.valueOf(id));
        } catch (Exception e) {
            log.error("Error while deleting role: " + e.getMessage(), e);
            throw new RuntimeException("Failed to delete role.", e);
        }
    }
}
