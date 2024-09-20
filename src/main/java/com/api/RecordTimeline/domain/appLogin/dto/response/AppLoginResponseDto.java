package com.api.RecordTimeline.domain.appLogin.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
public class AppLoginResponseDto extends ResponseDto {

    private String accessToken;
    private String refreshToken;

    private AppLoginResponseDto(String accessToken, String refreshToken) {
        super();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static ResponseEntity<AppLoginResponseDto> success(String accessToken, String refreshToken) {
        AppLoginResponseDto responseBody = new AppLoginResponseDto(accessToken, refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> memberNotFound() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> appLoginFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.APP_LOGIN_FAIL, ResponseMessage.APP_LOGIN_FAIL);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
