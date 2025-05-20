package com.theo.SelaluAda.controller;

import com.theo.SelaluAda.dto.RegisterStaffReqDTO;
import com.theo.SelaluAda.dto.StaffResponseDTO;
import com.theo.SelaluAda.dto.UpdateStaffRequestDTO;
import com.theo.SelaluAda.model.Branch;
import com.theo.SelaluAda.model.Role;
import com.theo.SelaluAda.repository.BranchRepository;
import com.theo.SelaluAda.repository.RoleRepository;
import com.theo.SelaluAda.security.CustomUserDetails;
import com.theo.SelaluAda.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;


    @PostMapping("/register")
    public ResponseEntity<?> registerStaff(@RequestBody RegisterStaffReqDTO requestDTO) {
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String response = staffService.registerStaff(requestDTO, currentUser);
        return response.startsWith("✅") ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<StaffResponseDTO> staffs = staffService.getAllStaffs();
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        StaffResponseDTO result = staffService.getStaffById(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateStaffRequestDTO dto) {
        String response = staffService.updateStaff(id, dto);
        return response.startsWith("✅") ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String response = staffService.deleteStaff(id);
        return response.startsWith("✅") ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

    // 📌 Tambahan untuk ambil list cabang
    @GetMapping("/branches")
    public ResponseEntity<List<Branch>> getAllBranches() {
        return ResponseEntity.ok(branchRepository.findAll());
    }

    // 📌 Tambahan untuk ambil list role
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }
}