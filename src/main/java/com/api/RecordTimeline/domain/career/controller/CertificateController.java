package com.api.RecordTimeline.domain.career.controller;

import com.api.RecordTimeline.domain.career.domain.Certificate;
import com.api.RecordTimeline.domain.career.dto.CertificateDto;
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
    public ResponseEntity<SuccessResponse<CertificateDto>> addCertificate(@RequestBody Certificate certificate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        certificate = certificate.toBuilder().userEmail(email).date(certificate.getDate().withDayOfMonth(1)).build();
        Certificate savedCertificate = certificateService.addCertificate(certificate);
        CertificateDto certificateDto = new CertificateDto(savedCertificate.getId(), savedCertificate.getName(), savedCertificate.getDate(), savedCertificate.getUserEmail());
        return ResponseEntity.ok(new SuccessResponse<>(certificateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<CertificateDto>> updateCertificate(@PathVariable Long id, @RequestBody Certificate certificate) {
        Certificate updatedCertificate = certificateService.updateCertificate(id, certificate);
        CertificateDto certificateDto = new CertificateDto(updatedCertificate.getId(), updatedCertificate.getName(), updatedCertificate.getDate(), updatedCertificate.getUserEmail());
        return ResponseEntity.ok(new SuccessResponse<>(certificateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Long>> deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificate(id);
        return ResponseEntity.ok(new SuccessResponse<>(id));
    }
}