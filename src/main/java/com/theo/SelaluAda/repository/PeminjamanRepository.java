package com.theo.SelaluAda.repository;


import com.theo.SelaluAda.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PeminjamanRepository  extends JpaRepository<Peminjaman, UUID> {
    @Query("SELECT COALESCE(SUM(p.jumlah_pinjaman), 0) FROM Peminjaman p WHERE p.sisa_tenor = 0 AND p.id_customer.id_customer = :userId")
    Double getTotalPeminjamanLunasByUser(UUID userId);
}
