package com.theo.SelaluAda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;
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

    @Column(nullable = false, unique = true)
    private String username; //nama konsumen

    @Column(name ="email", nullable = false, unique = true, length = 100)
    private String email; //email

    @Column(name ="password", nullable = false, length = 12)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "Id_role")
    private Role role;

    @Column(nullable = false)
    private String nama_lengkap;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserCustomer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserStaff staff;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FCMToken> fcmTokens = new ArrayList<>();
}
