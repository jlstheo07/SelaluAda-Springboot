package com.theo.SelaluAda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table (name ="User")

public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id_user;

    @Column(name = "nama", nullable = false, length = 100 )
    private String name;

    @Column(name ="email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name ="password", nullable = false, length = 12)
    private String password;

    @Column(name = "id_role", nullable = false)
    private Long id_role;
}
