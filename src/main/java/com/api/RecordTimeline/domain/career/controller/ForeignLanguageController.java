package com.api.RecordTimeline.domain.career.controller;

import com.api.RecordTimeline.domain.career.domain.ForeignLanguage;
import com.api.RecordTimeline.domain.career.domain.Proficiency;
import com.api.RecordTimeline.domain.career.dto.ForeignLanguageDto;
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
    public ResponseEntity<SuccessResponse<ForeignLanguageDto>> addLanguage(@RequestBody ForeignLanguageDto languageDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        ForeignLanguage language = ForeignLanguage.builder()
                .languageName(languageDto.getLanguageName())
                .proficiency(Proficiency.fromLevel(languageDto.getProficiency()))  // DTO의 숫자를 Enum으로 변환
                .userEmail(email)
                .build();

        ForeignLanguage savedLanguage = foreignLanguageService.addLanguage(language);
        ForeignLanguageDto responseDto = new ForeignLanguageDto(
                savedLanguage.getId(),
                savedLanguage.getLanguageName(),
                savedLanguage.getProficiency().getLevel(),  // Enum을 숫자로 변환하여 반환
                savedLanguage.getUserEmail()
        );
        return ResponseEntity.ok(new SuccessResponse<>(responseDto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<ForeignLanguageDto>> updateLanguage(@PathVariable Long id, @RequestBody ForeignLanguage language) {
        ForeignLanguage updatedLanguage = foreignLanguageService.updateLanguage(id, language);
        ForeignLanguageDto languageDto = new ForeignLanguageDto(updatedLanguage.getId(), updatedLanguage.getLanguageName(), updatedLanguage.getProficiency().getLevel(), updatedLanguage.getUserEmail());
        return ResponseEntity.ok(new SuccessResponse<>(languageDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Long>> deleteLanguage(@PathVariable Long id) {
        foreignLanguageService.deleteLanguage(id);
        return ResponseEntity.ok(new SuccessResponse<>(id));
    }
}