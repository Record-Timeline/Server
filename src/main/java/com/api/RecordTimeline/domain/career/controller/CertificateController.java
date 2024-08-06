package com.api.RecordTimeline.domain.career.controller;

import com.api.RecordTimeline.domain.career.domain.Certificate;
import com.api.RecordTimeline.domain.career.service.CertificateService;
import com.api.RecordTimeline.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping
    public ResponseEntity<SuccessResponse<Certificate>> addCertificate(@RequestBody Certificate certificate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        certificate = certificate.toBuilder().userEmail(email).date(certificate.getDate().withDayOfMonth(1)).build();
        Certificate savedCertificate = certificateService.addCertificate(certificate);
        return ResponseEntity.ok(new SuccessResponse<>(savedCertificate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<Certificate>> updateCertificate(@PathVariable Long id, @RequestBody Certificate certificate) {
        Certificate existingCertificate = certificateService.getCertificateById(id);
        Certificate updatedCertificate = certificateService.updateCertificate(id, certificate);
        return ResponseEntity.ok(new SuccessResponse<>(updatedCertificate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Long>> deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificate(id);
        return ResponseEntity.ok(new SuccessResponse<>(id));
    }
}
