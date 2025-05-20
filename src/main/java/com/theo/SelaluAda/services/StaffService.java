package com.theo.SelaluAda.services;

import com.theo.SelaluAda.dto.RegisterStaffReqDTO;
import com.theo.SelaluAda.dto.StaffResponseDTO;
import com.theo.SelaluAda.dto.UpdateStaffRequestDTO;
import com.theo.SelaluAda.model.Branch;
import com.theo.SelaluAda.model.Role;
import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.model.UserStaff;
import com.theo.SelaluAda.repository.BranchRepository;
import com.theo.SelaluAda.repository.RoleRepository;
import com.theo.SelaluAda.repository.StaffRepository;
import com.theo.SelaluAda.repository.UserRepository;
import com.theo.SelaluAda.security.CustomUserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StaffService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    private final StaffRepository staffRepository;

    public StaffService(UserRepository userRepository,
                        RoleRepository roleRepository,
                        BranchRepository branchRepository,
                        PasswordEncoder passwordEncoder,
                        StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.branchRepository = branchRepository;
        this.passwordEncoder = passwordEncoder;
        this.staffRepository = staffRepository;
    }

    public String registerStaff(RegisterStaffReqDTO requestDTO, CustomUserDetails currentUser) {
        if (!"SUPERADMIN".equalsIgnoreCase(currentUser.getUser().getRole().getNamaRole())) {
            return "❌ Hanya SUPERADMIN yang dapat mendaftarkan staff.";
        }

        if (userRepository.findByUsername(requestDTO.getUsername()).isPresent()) {
            return "❌ Username sudah digunakan.";
        }

        Optional<Role> roleOpt = roleRepository.findById(requestDTO.getRoleId());
        Optional<Branch> branchOpt = branchRepository.findById(requestDTO.getBranchId());

        if (roleOpt.isEmpty()) return "❌ Role tidak ditemukan.";
        if (branchOpt.isEmpty()) return "❌ Branch tidak ditemukan.";

        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setEmail(requestDTO.getEmail());
        user.setNama_lengkap(requestDTO.getNamaLengkap());
        user.setRole(roleOpt.get());
        userRepository.save(user);

        UserStaff staff = new UserStaff();
        staff.setNip(requestDTO.getNip());
        staff.setBranch(branchOpt.get());
        staff.setUser(user);

        staffRepository.save(staff);

        return "✅ Staff berhasil didaftarkan.";
    }

    public List<StaffResponseDTO> getAllStaffs() {
        return staffRepository.findAll().stream().map(stf -> new StaffResponseDTO(
                stf.getStaff_id(),
                stf.getNip(),
                stf.getBranch().getNamaCabang(),
                stf.getUser().getUsername(),
                stf.getUser().getEmail(),
                stf.getUser().getNama_lengkap(),
                stf.getUser().getRole().getNamaRole()
        )).collect(Collectors.toList());
    }

    public StaffResponseDTO getStaffById(UUID id) {
        return staffRepository.findById(id).map(stf -> new StaffResponseDTO(
                stf.getStaff_id(),
                stf.getNip(),
                stf.getBranch().getNamaCabang(),
                stf.getUser().getUsername(),
                stf.getUser().getEmail(),
                stf.getUser().getNama_lengkap(),
                stf.getUser().getRole().getNamaRole()
        )).orElse(null);
    }

    public String deleteStaff(UUID id) {
        Optional<UserStaff> empOpt = staffRepository.findById(id);
        if (empOpt.isPresent()) {
            staffRepository.deleteById(id);
            userRepository.deleteById(empOpt.get().getUser().getId_user());
            return "✅ Staff berhasil dihapus.";
        }
        return "❌ Staff tidak ditemukan.";
    }

    public String updateStaff(UUID id, UpdateStaffRequestDTO requestDTO) {
        Optional<UserStaff> empOpt = staffRepository.findById(id);
        if (empOpt.isEmpty()) return "❌ Staff tidak ditemukan.";

        UserStaff staff = empOpt.get();
        User user = staff.getUser();

        Optional<Role> roleOpt = roleRepository.findByNamaRole(requestDTO.getRoleName());
        Optional<Branch> branchOpt = branchRepository.findByNamaCabang(requestDTO.getBranchName());
        if (roleOpt.isEmpty()) return "❌ Role tidak ditemukan.";
        if (branchOpt.isEmpty()) return "❌ Branch tidak ditemukan.";

        user.setRole(roleOpt.get());
        userRepository.save(user);

        staff.setBranch(branchOpt.get());
        staffRepository.save(staff);

        return "✅ Staff berhasil diupdate.";
    }
}