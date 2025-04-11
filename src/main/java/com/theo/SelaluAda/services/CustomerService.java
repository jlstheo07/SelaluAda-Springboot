package com.theo.SelaluAda.services;

import com.theo.SelaluAda.model.Plafond;
import com.theo.SelaluAda.model.UserCustomer;
import com.theo.SelaluAda.repository.CustomerRepository;
import com.theo.SelaluAda.repository.PlafondRepository;
import com.theo.SelaluAda.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
//    @Autowired
//    private CustomerRepository CustomerRepository;
//
//    @Autowired
//    private PlafondRepository plafonRepository;
//
//    @Autowired
//    PeminjamanService peminjamanService;
//
//
//    private final JwtUtil jwtUtil;
//
//    @Autowired
//    public CustomerService(JwtUtil jwtUtil) {  // Constructor Injection
//        this.jwtUtil = jwtUtil;
//    }
//
//
//
//    @Transactional
//    public ResponseEntity<?> cekUpdateAkun(UUID id_user) {
//        Optional<UserCustomer> userCustomerOptional = CustomerRepository.findByUserIdUser(id_user);
//        if (userCustomerOptional.isPresent()) {
//            return ResponseEntity.ok(userCustomerOptional.get());
//        }
//        else {
//            return ResponseEntity
//                    .status(404) // HTTP 404 Not Found
//                    .body("{\"response\":\"Silakan update akun terlebih dahulu\"}");
//        }
//
//    }
//
//    public UserCustomer addCustomer(UserCustomer usersCustomer) {
//        return CustomerRepository.save(usersCustomer);
//    }
//
//
//
//    @Transactional
//    public UserCustomer partialUpdate(UUID id, Map<String, Object> updates) {
//        return CustomerRepository.findById(id).map(existingCustomer -> {
//            updates.forEach((key, value) -> {
//                Field field;
//                try {
//                    field = UserCustomer.class.getDeclaredField(key);
//                    field.setAccessible(true);
//                    field.set(existingCustomer, value);
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    throw new RuntimeException("Error updating field: " + key);
//                }
//            });
//            return CustomerRepository.save(existingCustomer);
//        }).orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
//    }
//
//    public UUID getUserCustomerIdFromToken(String token) {
//        // Ambil id_user langsung dari token JWT
//        UUID idUser = UUID.fromString(jwtUtil.extractidUser(token));
//
//        // Cari id_user_customer berdasarkan id_user
//        return CustomerRepository.findByUsersIdUser(idUser)
//                .map(UserCustomer::getId_customer)
//                .orElseThrow(() -> new RuntimeException("User Customer not found"));
//    }
//
//    public UserCustomer getPlafon(String token) {
//        List<Plafond> plafons = plafonRepository.findAllSorted();
//        Double jumlPinjLunas = peminjamanService.getTotalPeminjamanLunasByUser("Bearer "+token);
//        Plafond plafon = plafons.get(0);
//        for (int i = 0; i < plafons.size(); i++){
//            if(jumlPinjLunas>plafons.get(i).getJumlah_plafon()){
//                plafon = plafons.get(i);
//            }
//            else{
//                break;
//            }
//        }
//
//        UserCustomer usersCustomer = CustomerRepository.findById(getUserCustomerIdFromToken(token)).get();
//        usersCustomer.setPlafond(plafon);
//
//
//        return CustomerRepository.save(usersCustomer);
//    }
}