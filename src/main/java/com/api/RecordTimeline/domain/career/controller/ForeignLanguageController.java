package com.api.RecordTimeline.domain.career.controller;

import com.api.RecordTimeline.domain.career.domain.ForeignLanguage;
import com.api.RecordTimeline.domain.career.service.ForeignLanguageService;
import com.api.RecordTimeline.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
public class ForeignLanguageController {

    private final ForeignLanguageService foreignLanguageService;

    @PostMapping
    public ResponseEntity<SuccessResponse<ForeignLanguage>> addLanguage(@RequestBody ForeignLanguage language) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        language = language.toBuilder().userEmail(email).build();
        ForeignLanguage savedLanguage = foreignLanguageService.addLanguage(language);
        return ResponseEntity.ok(new SuccessResponse<>(savedLanguage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<ForeignLanguage>> updateLanguage(@PathVariable Long id, @RequestBody ForeignLanguage language) {
        ForeignLanguage existingLanguage = foreignLanguageService.getLanguageById(id);
        ForeignLanguage updatedLanguage = foreignLanguageService.updateLanguage(id, language);
        return ResponseEntity.ok(new SuccessResponse<>(updatedLanguage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Long>> deleteLanguage(@PathVariable Long id) {
        foreignLanguageService.deleteLanguage(id);
        return ResponseEntity.ok(new SuccessResponse<>(id));
    }
}
