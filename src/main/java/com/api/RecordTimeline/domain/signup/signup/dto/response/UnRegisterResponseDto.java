package com.api.RecordTimeline.domain.signup.signup.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class UnRegisterResponseDto extends ResponseDto {

    public static ResponseEntity<UnRegisterResponseDto> success() {
        UnRegisterResponseDto responseBody = new UnRegisterResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> memberNotFound() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> passwordMismatch() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.PASSWORD_MISMATCH, ResponseMessage.PASSWORD_MISMATCH);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
