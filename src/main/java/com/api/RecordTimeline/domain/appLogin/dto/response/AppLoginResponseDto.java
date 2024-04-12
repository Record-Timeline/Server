package com.api.RecordTimeline.domain.appLogin.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
public class AppLoginResponseDto extends ResponseDto {

    private String token;

    private AppLoginResponseDto(String token){
        super();
        this.token = token;
    }

    public static ResponseEntity<AppLoginResponseDto> success (String token) {
        AppLoginResponseDto responseBody = new AppLoginResponseDto(token);
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
