package com.theo.SelaluAda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserStaff")
public class UserStaff  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_staff;

    @OneToOne
    @JoinColumn(name = "id_user", unique = true) // Relasi ke Users
    private User users;

    @Column(nullable = false)
    private Integer nip;

    @OneToOne
    @JoinColumn(name = "id_branch", unique = true)
    private Branch id_branch;

    @Column(nullable = false)
    private String jabatan;
}