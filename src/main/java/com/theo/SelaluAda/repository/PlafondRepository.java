package com.theo.SelaluAda.repository;


import com.theo.SelaluAda.model.Plafond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlafondRepository extends JpaRepository<Plafond, UUID> {
    @Query("SELECT p FROM Plafond p ORDER BY p.jumlah_plafon ASC")
    List<Plafond> findAllSorted();
}