package com.api.RecordTimeline.domain.appLogin.controller;

import com.api.RecordTimeline.domain.appLogin.dto.request.AppLoginRequestDto;
import com.api.RecordTimeline.domain.appLogin.dto.response.AppLoginResponseDto;
import com.api.RecordTimeline.domain.appLogin.service.AppLoginService;
import com.api.RecordTimeline.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AppLoginController {

    private final AppLoginService appLoginService;

    @Operation(summary = "앱 자체 로그인", description = "앱 자체 로그인을 시도합니다.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "앱 자체 로그인 성공",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AppLoginResponseDto.class)))
    })
    @PostMapping("/app-login")
    public ResponseEntity<SuccessResponse<AppLoginResponseDto>> appLogin(@RequestBody @Valid AppLoginRequestDto requestBody) {
        AppLoginResponseDto response = appLoginService.appLogin(requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }
}
