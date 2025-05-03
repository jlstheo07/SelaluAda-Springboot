package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.model.UserStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<UserStaff, UUID> {
    Optional<UserStaff> findByUser(UserStaff user);
}
