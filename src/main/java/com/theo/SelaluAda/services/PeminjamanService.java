package com.theo.SelaluAda.services;

import org.springframework.stereotype.Service;

@Service
public class PeminjamanService {
//    @Autowired
//    private PeminjamanRepository peminjamanRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    private final CustomerService customerService;
//
//    public PeminjamanService(@Lazy CustomerService customerService) {
//        this.customerService = customerService;
//    }
//
//    public Peminjaman partialUpdatePinjaman(UUID id, Map<String, Object> updates) {
//        Optional<Peminjaman> optionalPinjaman = peminjamanRepository.findById(id);
//        if (optionalPinjaman.isEmpty()) {
//            throw new RuntimeException("Pinjaman dengan ID " + id + " tidak ditemukan.");
//        }
//
//        Peminjaman existingPinjaman = optionalPinjaman.get();
//        Class<?> clazz = existingPinjaman.getClass();
//
//        for (Map.Entry<String, Object> entry : updates.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//
//            try {
//                Field field = clazz.getDeclaredField(key);
//                field.setAccessible(true);
//                // Konversi nilai ke tipe data yang sesuai
//                if (value != null) {
//                    if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
//                        field.set(existingPinjaman, ((Number) value).doubleValue());
//                    } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
//                        field.set(existingPinjaman, ((Number) value).intValue());
//                    } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
//                        field.set(existingPinjaman, ((Number) value).longValue());
//                    } else {
//                        field.set(existingPinjaman, value);
//                    }
//                }
//            } catch (NoSuchFieldException e) {
//                throw new RuntimeException("Field \"" + key + "\" tidak ditemukan pada Pinjaman.");
//            } catch (IllegalAccessException | ClassCastException e) {
//                throw new RuntimeException("Gagal memperbarui field \"" + key + "\". Tipe data tidak sesuai.");
//            }
//        }
//
//        return peminjamanRepository.save(existingPinjaman);
//    }
//
//    public Peminjaman addPeminjaman(PengajuanToPeminjamanRequest pengajuan) {
//        UserCustomer usersCustomer = customerRepository.findById(pengajuan.getId_customer()).get();
//        Peminjaman peminjaman = Peminjaman.builder()
//                .jumlah_peminjaman(pengajuan.getAmount())
//                .bunga(pengajuan.getBunga())
//                .angsuran(pengajuan.getAngsuran())
//                .tenor(pengajuan.getTenor())
//                .sisa_tenor(pengajuan.getTenor())
//                .sisa_pokok_hutang(pengajuan.getAmount())
//                .id_user_customer(usersCustomer)
//                .build();
//        return peminjamanRepository.save(peminjaman);
//    }
//
//    public Double getTotalPeminjamanLunasByUser(String token) {
//        String tokenTrimp = token.substring(7); // Hapus "Bearer "
//        UUID idUserCustomer = customerService.getUserCustomerIdFromToken(tokenTrimp);
//
//        return peminjamanRepository.getTotalPeminjamanLunasByUser(idUserCustomer);
//    }
//
//



}
