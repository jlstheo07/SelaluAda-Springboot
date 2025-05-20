package com.theo.SelaluAda.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Role")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id") // ini untuk mencocokkan nama kolom di database
    private UUID roleId;

    @Column(name = "nama_role", nullable = false, unique = true)
    private String namaRole;
}