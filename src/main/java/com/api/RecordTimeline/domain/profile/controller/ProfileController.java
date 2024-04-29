package com.api.RecordTimeline.domain.profile.controller;

import com.api.RecordTimeline.domain.profile.service.ProfileService;
import com.api.RecordTimeline.domain.common.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "프로필 이미지 업로드 또는 수정", description = "사용자는 자신의 프로필 이미지를 업로드하거나 수정할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 이미지 업로드 또는 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping("/update-image")
    public ResponseEntity<ResponseDto> uploadProfileImage(@RequestParam("profileImage") MultipartFile profileImage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return profileService.updateProfileImage(email, profileImage);
    }

    @Operation(summary = "소개글 등록 또는 수정", description = "사용자는 자신의 소개글을 등록하거나 수정할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소개글 등록 또는 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping("/update-introduction")
    public ResponseEntity<ResponseDto> updateIntroduction(@RequestParam("introduction") String introduction) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return profileService.updateIntroduction(email, introduction);
    }

    @Operation(summary = "프로필 이미지 삭제", description = "사용자는 자신의 프로필 이미지를 삭제할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 이미지 삭제 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("/delete-image")
    public ResponseEntity<ResponseDto> deleteProfileImage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return profileService.deleteProfileImage(email);
    }

    @Operation(summary = "소개글 삭제", description = "사용자는 자신의 소개글을 삭제할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소개글 삭제 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("/delete-introduction")
    public ResponseEntity<ResponseDto> clearIntroduction() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return profileService.deleteIntroduction(email);
    }
}
