package com.api.RecordTimeline.domain.signup.controller;

import com.api.RecordTimeline.domain.signup.dto.request.EmailCheckResquestDto;
import com.api.RecordTimeline.domain.signup.dto.response.EmailCheckResponseDto;
import com.api.RecordTimeline.domain.signup.service.implement.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class SignupController {
    private final SignupService authService;
    @PostMapping("/email-check")
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck (@RequestBody @Valid EmailCheckResquestDto requestBody) {
        ResponseEntity<? super EmailCheckResponseDto> response = authService.emailCheck(requestBody);
        return response;
    }
}
