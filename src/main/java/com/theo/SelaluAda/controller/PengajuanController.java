package com.theo.SelaluAda.controller;

import com.theo.SelaluAda.dto.CreatePengajuanRequestDTO;
import com.theo.SelaluAda.dto.MarketingReviewRequestDTO;
import com.theo.SelaluAda.dto.ReviewManagerDTO;
import com.theo.SelaluAda.model.Pengajuan;
import com.theo.SelaluAda.services.PengajuanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pengajuan")
@RequiredArgsConstructor
public class PengajuanController {

    private final PengajuanService pengajuanService;

    @PostMapping
    public ResponseEntity<?> buatPengajuan(@RequestBody CreatePengajuanRequestDTO request,
                                           Principal principal) {
        pengajuanService.buatPengajuan(request, principal.getName());
        return ResponseEntity.ok("Pengajuan berhasil dibuat");
    }

    @PutMapping("/{id}/review-marketing")
    public ResponseEntity<?> reviewPengajuanOlehMarketing(@PathVariable UUID id,
                                                          @RequestBody MarketingReviewRequestDTO request,
                                                          Principal principal) {
        pengajuanService.reviewOlehMarketing(id, request, principal.getName());
        return ResponseEntity.ok("Review marketing berhasil diproses");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Pengajuan>> getPengajuanPendingUntukMarketing(Principal principal) {
        List<Pengajuan> list = pengajuanService.getPengajuanPendingUntukMarketing(principal.getName());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}/review-manager")
    public ResponseEntity<String> reviewByManager(
            @PathVariable UUID id,
            @RequestBody ReviewManagerDTO request,
            Principal principal) {

        pengajuanService.reviewByBranchManager(id, principal.getName(), request.getDisetujui(), request.getCatatan());
        return ResponseEntity.ok("Pengajuan telah diproses oleh Branch Manager");
    }

    @GetMapping("/pending-manager")
    public ResponseEntity<List<Pengajuan>> getPengajuanPendingUntukManager(Principal principal) {
        List<Pengajuan> list = pengajuanService.getPengajuanPendingUntukManager(principal.getName());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}/disburse")
    public ResponseEntity<String> disbursePengajuan(
            @PathVariable UUID id,
            Principal principal) {

        pengajuanService.disbursePengajuan(id, principal.getName());
        return ResponseEntity.ok("Pengajuan telah dicairkan");
    }
}