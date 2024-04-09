package com.api.RecordTimeline.domain.signup.email.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import com.api.RecordTimeline.domain.common.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CheckCertificationResponseDto extends ResponseDto {
    private CheckCertificationResponseDto(){
        super();
    }
    public static ResponseEntity<CheckCertificationResponseDto> success() {
        CheckCertificationResponseDto responseBody = new CheckCertificationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> certificationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.CERTIFICATION_FAILED, ResponseMessage.CERTIFICATION_FAILED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
