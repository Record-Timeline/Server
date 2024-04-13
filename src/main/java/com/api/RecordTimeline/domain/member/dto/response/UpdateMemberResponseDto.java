package com.api.RecordTimeline.domain.member.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UpdateMemberResponseDto extends ResponseDto{

    private UpdateMemberResponseDto() {
        super();
    }

    public static ResponseEntity<UpdateMemberResponseDto> success() {
        UpdateMemberResponseDto responseBody = new UpdateMemberResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> notAuthorized() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_AUTHORIZED, ResponseMessage.NOT_AUTHORIZED);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> memberNotFound() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateNickname() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
