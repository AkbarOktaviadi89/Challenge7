package com.binarfud.challenge7.Config;

import com.binarfud.challenge7.Enum.ERole;
import com.binarfud.challenge7.Model.Roles;
import com.binarfud.challenge7.Repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Config {

    Config(RoleRepository roleRepository) {
        log.info("Cheking roles presented");
        for(ERole c : ERole.values()) {
            try {
                Roles roles = roleRepository.findByRoleName(c)
                        .orElseThrow(() -> new RuntimeException("Roles not found"));
                log.info("Role {} has been found!", roles.getRoleName());
            } catch(RuntimeException rte) {
                log.info("Role {} is not found, inserting to DB . . .", c.name());
                Roles roles = new Roles();
                roles.setRoleName(c);
                roleRepository.save(roles);
            }
        }
    }
}
