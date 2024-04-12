package com.api.RecordTimeline.domain.signup.signup.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@Getter
public class SignupResponseDto extends ResponseDto {
    private String token;
    private SignupResponseDto(){
        super();
    }

    public static ResponseEntity<SignupResponseDto> success(String token) {
        SignupResponseDto responseBody = new SignupResponseDto();
        responseBody.token = token;
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    public static ResponseEntity<ResponseDto> duplicateEmail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateNickname() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> invalidPasswordPattern() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.INVALID_PASSWORD_PATTERN, ResponseMessage.INVALID_PASSWORD_PATTERN);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
