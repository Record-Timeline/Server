package com.api.RecordTimeline.global.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@ToString
public enum ErrorType {

    // ------------------------------------------ S3 ------------------------------------------
    EXCEEDING_FILE_COUNT(BAD_REQUEST, "S4001", "사진 개수가 너무 많습니다."),
    EXCEEDING_FILE_SIZE(BAD_REQUEST, "S4002", "사진 용량이 너무 큽니다."),
    S3_UPLOAD(INTERNAL_SERVER_ERROR, "S5001", "서버오류, S3 사진 업로드 에러입니다."),
    S3_CONNECT(INTERNAL_SERVER_ERROR, "S5002", "서버오류, S3 연결 에러입니다."),
    S3_CONVERT(INTERNAL_SERVER_ERROR, "S5003", "서버오류, S3 변환 에러입니다."),

    // ------------------------------------------ USER -------------------------------------------
    _USER_NOT_FOUND_BY_TOKEN(OK, "USER_4040", "제공된 토큰으로 사용자를 찾을 수 없습니다."),
    _UNAUTHORIZED(OK, "USER_4010", "로그인되지 않은 상태입니다."),
    _USER_NOT_FOUND_DB(OK, "USER_4041", "존재하지 않는 회원입니다."),

    // ------------------------------------------ PROFILE ------------------------------------------
    _NO_IMAGE_FOUND(OK, "PROFILE4040", "이미지를 찾을 수 없습니다."),
    _NO_PROFILE_FOUND(OK, "PROFILE4041", "사용자의 프로필이 존재하지 않습니다."),
    _UPDATE_FAILED(OK, "PROFILE5050", "업데이트에 실패했습니다."),

    // ------------------------------------------ _DATABASE ------------------------------------------
    _DATABASE_ERROR(OK, "DATABASE5050", "데이터베이스 오류입니다."),

    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    ErrorType(final HttpStatus status, final String errorCode, final String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
