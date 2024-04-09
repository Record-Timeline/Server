package com.api.RecordTimeline.domain.signup.signup.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class NicknameCheckResponseDto extends ResponseDto {
    private NicknameCheckResponseDto() {
        super();
    }
    public static ResponseEntity<NicknameCheckResponseDto> success() {
        NicknameCheckResponseDto responseBody = new NicknameCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> duplicateNickname() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
