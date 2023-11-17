package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Model.Roles;
import com.binarfud.challenge7.Service.RolesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Transactional
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @Operation(summary = "Get All Roles")
    @GetMapping("/get-roles")
    public ResponseEntity<List<Roles>> getAllRoles() {
        try {
            List<Roles> roles = rolesService.getAllRoles();
            if (roles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    @Operation(summary = "Get Role By Id")
    @GetMapping("/get-roles/{id}")
    public ResponseEntity<Roles> getRoleById(@PathVariable("id") Integer id) {
        try {
            Optional<Roles> roleData = rolesService.getRoleById(id);
            if (roleData.isPresent()) {
                return new ResponseEntity<>(roleData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create New Role")
    @PostMapping("/create-roles")
    public ResponseEntity<Roles> createRole(@RequestBody Roles role) {
        try {
            Roles _role = new Roles(role.getRoleName());
            if (rolesService.addNewRole(_role)) {
                return new ResponseEntity<>(_role, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Edit Role By Id")
    @PutMapping("/edit-roles/{id}")
    public ResponseEntity<Roles> updateRole(@PathVariable("id") Integer id, @RequestBody Roles role) {
        Optional<Roles> roleData = rolesService.getRoleById(id);
        try {
            if (roleData.isPresent()) {
                Roles _role = roleData.get();
                _role.setRoleName(role.getRoleName());
                rolesService.updateRole(_role);
                return new ResponseEntity<>(_role, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Role By Id")
    @DeleteMapping("/delete-roles/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable("id") Integer id) {
        try {
            rolesService.deleteRoleById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
