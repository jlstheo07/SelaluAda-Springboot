package com.theo.SelaluAda.services;

import com.theo.SelaluAda.dto.FeatureDTO;
import com.theo.SelaluAda.model.Feature;
import com.theo.SelaluAda.model.Role;
import com.theo.SelaluAda.model.RoleFeature;
import com.theo.SelaluAda.repository.FeatureRepository;
import com.theo.SelaluAda.repository.RoleFeatureRepository;
import com.theo.SelaluAda.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleFeatureService {

    private final RoleRepository roleRepository;
    private final FeatureRepository featureRepository;
    private final RoleFeatureRepository roleFeatureRepository;

    public List<FeatureDTO> getFeaturesByRole(UUID roleId) {
        List<RoleFeature> roleFeatures = roleFeatureRepository.findByRole_RoleId(roleId);
        return roleFeatures.stream()
                .map(rf -> new FeatureDTO(rf.getFeature().getFeatureId(), rf.getFeature().getNamaFeature()))
                .toList();
    }

    public List<FeatureDTO> getAllFeatures() {
        return featureRepository.findAll().stream()
                .map(feature -> new FeatureDTO(feature.getFeatureId(), feature.getNamaFeature()))
                .toList();
    }

    public void updateRoleFeatures(UUID roleId, List<UUID> featureIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Hapus fitur sebelumnya
        roleFeatureRepository.deleteByRole_RoleId(roleId);

        // Tambahkan fitur baru
        List<Feature> features = featureRepository.findAllById(featureIds);
        List<RoleFeature> roleFeatures = features.stream()
                .map(feature -> new RoleFeature(null, role, feature))
                .toList();

        roleFeatureRepository.saveAll(roleFeatures);
    }
}