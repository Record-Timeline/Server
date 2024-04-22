package com.api.RecordTimeline.domain.profile.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import com.api.RecordTimeline.domain.signup.signup.dto.response.SignupResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class ProfileResponseDto extends ResponseDto {
    public static ResponseEntity<ResponseDto> success() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> memberNotFound() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> updateFailed() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> imageDeleted() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.IMAGE_DELETED);
        return ResponseEntity.ok(responseBody);
    }

    public static ResponseEntity<ResponseDto> noImageFound() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_IMAGE_FOUND, ResponseMessage.NO_IMAGE_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> introductionCleared() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.INTRODUCTION_CLEARED);
        return ResponseEntity.ok(responseBody);
    }

    public static ResponseEntity<ResponseDto> noProfileFound() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_PROFILE_FOUND, ResponseMessage.NO_PROFILE_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
