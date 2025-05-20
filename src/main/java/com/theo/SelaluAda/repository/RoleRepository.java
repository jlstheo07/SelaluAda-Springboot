package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.model.Role;
import com.theo.SelaluAda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByNamaRole(String namaRole);
}