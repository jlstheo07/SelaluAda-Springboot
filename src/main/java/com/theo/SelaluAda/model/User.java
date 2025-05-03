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
@Table (name ="Users")

public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id_user;

    @Column(name = "nama", nullable = false, length = 100 )
    private String username; //username

    @Column(nullable = false)
    private String nama_lengkap;

    @Column(name ="email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name ="password", nullable = false, length = 12)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "Id_role")
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserStaff staff;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserCustomer customer;


}
