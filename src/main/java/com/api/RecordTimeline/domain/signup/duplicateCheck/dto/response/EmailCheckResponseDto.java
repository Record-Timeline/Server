package com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import com.api.RecordTimeline.domain.common.ResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class EmailCheckResponseDto extends ResponseDto {

    public static ResponseEntity<EmailCheckResponseDto> success() {
        EmailCheckResponseDto responseBody = new EmailCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateEmail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
