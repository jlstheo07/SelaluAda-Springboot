package com.theo.SelaluAda.controller;

import com.theo.SelaluAda.model.UserCustomer;
import com.theo.SelaluAda.services.CustomerService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
//
//@RestController
//@RequestMapping("/customer")
public class CustomerController {


//    @Autowired
//    private CustomerService CustomerService;
//    @Autowired
//    private CustomerService customerService;
//
//    @PostMapping("/CekUpdateAkun")
//    public ResponseEntity<?> cekUpdateAkun(@RequestBody CekUpdateAkunRequest request) {
//        UUID id_user = request.getId_user(); // Ambil ID dari request body
//        return CustomerService.cekUpdateAkun(id_user); // Panggil service untuk mendapatkan response
//    }
//
//    @PostMapping("/AddDetailAkun")
//    public ResponseEntity<UserCustomer> createCustomer(@RequestBody UserCustomer usersCustomer) {
//        UserCustomer savedCustomer = CustomerService.addCustomer(usersCustomer);
//        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
//    }
//
//
//    @PatchMapping("UpdateDetailCustomer/{id}")
//    public ResponseEntity<UserCustomer> updatePartialCustomer(
//            @PathVariable UUID id,
//            @RequestBody Map<String, Object> updates) {
//        UserCustomer updatedCustomer = CustomerService.partialUpdate(id, updates);
//        return ResponseEntity.ok(updatedCustomer);
//    }
//
//    @GetMapping("/getIdUserCustomer")
//    public
//    ResponseEntity<Map<String, UUID>>getMyUserCustomerId(@RequestHeader("Authorization") String authHeader) {
//        String token = authHeader.substring(7); // Hapus "Bearer "
//        UUID idUserCustomer = CustomerService.getUserCustomerIdFromToken(token);
//
//        Map<String, UUID> response = new HashMap<>();
//        response.put("id_user_customer", idUserCustomer);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/getPlafon")
//    public ResponseEntity<UserCustomer> getplafon(@RequestHeader("Authorization") String authHeader) {
//        String token = authHeader.substring(7);
//        // Hapus "Bearer ";
//        return ResponseEntity.ok(customerService.getPlafon(token));
//    }
//

}