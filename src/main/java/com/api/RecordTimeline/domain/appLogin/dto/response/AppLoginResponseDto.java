package com.api.RecordTimeline.domain.appLogin.dto.response;

import com.api.RecordTimeline.domain.common.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class AppLoginResponseDto extends ResponseDto {

    private String token;

    private AppLoginResponseDto(String token) {
        this.token = token;
    }

    public static AppLoginResponseDto success(String token) {
        return new AppLoginResponseDto(token);
    }
}
