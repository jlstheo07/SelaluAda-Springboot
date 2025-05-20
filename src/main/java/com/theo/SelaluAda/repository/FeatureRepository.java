package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeatureRepository extends JpaRepository<Feature, UUID> {
    boolean existsByNamaFeature(String namaFeature);
}