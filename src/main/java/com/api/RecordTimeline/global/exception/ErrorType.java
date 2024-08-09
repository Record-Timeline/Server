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
    INVALID_FILE_PATH(INTERNAL_SERVER_ERROR, "S5004", "잘못된 파일 경로입니다."),

    // ---------------------------------------- JWT TOKEN ----------------------------------------
    _JWT_PARSING_ERROR(BAD_REQUEST, "JWT_4001", "JWT Token이 올바르지 않습니다."),
    _JWT_EXPIRED(UNAUTHORIZED, "JWT_4010", "Jwt Token의 유효 기간이 만료되었습니다."),
    _JWT_NOT_FOUND(UNAUTHORIZED, "JWT_4010", "Jwt Token을 포함하셔야합니다."),

    // ------------------------------------------ USER -------------------------------------------
    _USER_NOT_FOUND_BY_TOKEN(OK, "USER_4040", "제공된 토큰으로 사용자를 찾을 수 없습니다."),
    _UNAUTHORIZED(OK, "USER_4010", "로그인되지 않은 상태입니다."),
    _USER_NOT_FOUND_DB(OK, "USER_4041", "존재하지 않는 회원입니다."),

    // ------------------------------------------ PROFILE ------------------------------------------
    _NO_IMAGE_FOUND(OK, "PROFILE4040", "이미지를 찾을 수 없습니다."),
    _NO_PROFILE_FOUND(OK, "PROFILE4041", "사용자의 프로필이 존재하지 않습니다."),
    _UPDATE_FAILED(OK, "PROFILE5050", "업데이트에 실패했습니다."),

    // ------------------------------------------ Timeline ------------------------------------------
    _DO_NOT_HAVE_PERMISSION (OK, "Timeline4000", "해당 작업에 권한이 없습니다."),
    ALREADY_EXISTS(BAD_REQUEST, "BOOKMARK_4000", "이미 북마크된 게시글입니다."),
    _SUBTIMELINE_NOT_FOUND(OK, "SUBTIMELINE_4040", "존재하지 않는 서브타임라인입니다."),
    _TIMELINE_NOT_FOUND(NOT_FOUND, "Timeline4040", "해당 타임라인을 찾을 수 없습니다."),

    // ------------------------------------------ Recommend ------------------------------------------
    _NO_RECOMMENDER_FOUND(OK, "RECOMMEND4040", "해당 관심사를 가진 사용자가 없습니다."),

    // ------------------------------------------ Search ------------------------------------------
    _NO_SEARCH_RESULTS(OK, "SEARCH4040", "검색 결과가 없습니다."),

    // ------------------------------------------ DATABASE ------------------------------------------
    _DATABASE_ERROR(OK, "DATABASE5050", "데이터베이스 오류입니다."),

    // ------------------------------------------ FOLLOW ------------------------------------------
    _FOLLOW_NOT_FOUND(OK, "FOLLOW4000", "팔로우를 찾을 수 없습니다."),
    _ALREADY_FOLLOWING(OK, "FOLLOW40001", "이미 팔로우되어 있습니다.");



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
