package com.theo.SelaluAda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FeatureDTO {
    private UUID featureId;
    private String namaFeature;
}