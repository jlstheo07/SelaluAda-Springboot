package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.enums.ProvinceToBranch;
import com.theo.SelaluAda.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {
    Optional<Branch> findFirstByArea(ProvinceToBranch area);
    Optional<Branch> findByNamaCabang(String namaCabang);
}