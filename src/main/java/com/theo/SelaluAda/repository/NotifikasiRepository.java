package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.model.Notifikasi;
import com.theo.SelaluAda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotifikasiRepository extends JpaRepository<Notifikasi, UUID> {
    List<Notifikasi> findByUserOrderByWaktuDibuatDesc(User user);
}