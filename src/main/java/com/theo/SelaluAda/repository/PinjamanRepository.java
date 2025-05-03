package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.model.Pinjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PinjamanRepository extends JpaRepository<Pinjaman, UUID> {

}