package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.model.RoleFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleFeatureRepository extends JpaRepository<RoleFeature, UUID> {
    List<RoleFeature> findByRole_RoleId(UUID roleId);
    void deleteByRole_RoleId(UUID roleId);
    boolean existsByRole_RoleIdAndFeature_FeatureId(UUID roleId, UUID featureId);
}