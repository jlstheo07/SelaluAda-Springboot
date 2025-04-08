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
    private UUID Id_role;

    @Column(nullable = false, unique = true)
    private String name_role;

    public Role(String uuid, String customer) {
        this.Id_role = UUID.fromString(uuid);
        this.name_role = customer;
    }
}