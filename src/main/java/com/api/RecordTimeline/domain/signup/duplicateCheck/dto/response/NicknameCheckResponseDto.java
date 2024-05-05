package com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import com.api.RecordTimeline.domain.common.ResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public class NicknameCheckResponseDto extends ResponseDto {
    public static ResponseEntity<NicknameCheckResponseDto> success() {
        NicknameCheckResponseDto responseBody = new NicknameCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> duplicateNickname() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
