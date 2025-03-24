package com.theo.SelaluAda.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table  (name = "role_to_feature")

public class RoleToFeature {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name= "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name="feature_id")
    private Feature feature;

}
