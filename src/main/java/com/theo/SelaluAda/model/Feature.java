package com.theo.SelaluAda.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "features")

public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "feature_id")
    private UUID featureId;

    @Column(nullable = false)
    private String namaFeature;
}