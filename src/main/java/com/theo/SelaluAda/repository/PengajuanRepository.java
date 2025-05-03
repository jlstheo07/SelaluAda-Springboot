package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.model.Pengajuan;
import com.theo.SelaluAda.model.UserStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PengajuanRepository extends JpaRepository<Pengajuan, UUID> {
    List<Pengajuan> findByBranchManagerAndStatus(UserStaff manager, String status);

    // Hitung jumlah pengajuan aktif yang ditangani oleh marketing tertentu
    @Query("SELECT COUNT(p) FROM Pengajuan p WHERE p.marketing = :marketing AND p.status IN ('PENDING', 'REVIEWED')")
    int countActiveByMarketing(@Param("marketing") UserStaff marketing);
}