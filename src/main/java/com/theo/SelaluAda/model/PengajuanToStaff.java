package com.theo.SelaluAda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "PengajuanToStaff")
public class PengajuanToStaff {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id_pengajuan_to_staff;

    @ManyToOne
    @JoinColumn(name = "id_staff")
    private UserStaff id_staff;

    @ManyToOne
    @JoinColumn(name="id_pengajuan")
    private Pengajuan id_pengajuan;
}
