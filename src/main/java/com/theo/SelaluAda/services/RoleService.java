package com.theo.SelaluAda.services;

import com.theo.SelaluAda.model.Role;
import com.theo.SelaluAda.repository.RoleRepository;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepository RoleRepository;

    public RoleService(RoleRepository RoleRepository) {
        this.RoleRepository = RoleRepository;
    }

    public Role getById(UUID id) {
        return RoleRepository.findById(id).get();
    }
}
