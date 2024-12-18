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
    _JWT_EXPIRED(UNAUTHORIZED, "JWT_4002", "Jwt Token의 유효 기간이 만료되었습니다."),
    _JWT_NOT_FOUND(UNAUTHORIZED, "JWT_4003", "Jwt Token을 포함하셔야합니다."),
    _REFRESH_TOKEN_NOT_FOUND(OK, "JWT_4004", "리프레시 토큰이 존재하지 않습니다."),
    _REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "JWT_4005", "리프레시 토큰의 유효 기간이 만료되었습니다. 재로그인 해주세요"),
    _REFRESH_TOKEN_INVALID(OK, "JWT_4006", "리프레시 토큰이 유효하지 않습니다."),

    // ------------------------------------------ USER -------------------------------------------
    _USER_NOT_FOUND_BY_TOKEN(OK, "USER_4040", "제공된 토큰으로 사용자를 찾을 수 없습니다."),
    _UNAUTHORIZED(OK, "USER_4010", "로그인되지 않은 상태입니다."),
    _USER_NOT_FOUND_DB(OK, "USER_4041", "존재하지 않는 회원입니다."),
    _Login_Failed(OK, "USER_4042", "로그인에 실패했습니다."),

    // ------------------------------------------ PROFILE ------------------------------------------
    _NO_IMAGE_FOUND(OK, "PROFILE4040", "이미지를 찾을 수 없습니다."),
    _NO_PROFILE_FOUND(OK, "PROFILE4041", "사용자의 프로필이 존재하지 않습니다."),
    _UPDATE_FAILED(OK, "PROFILE5050", "업데이트에 실패했습니다."),

    // ------------------------------------------ Timeline ------------------------------------------
    _DO_NOT_HAVE_PERMISSION (OK, "PERMISSION4000", "해당 작업에 권한이 없습니다."),
    ALREADY_EXISTS(BAD_REQUEST, "BOOKMARK_4000", "이미 북마크된 게시글입니다."),
    _SUBTIMELINE_NOT_FOUND(OK, "SUBTIMELINE_4040", "존재하지 않는 서브타임라인입니다."),
    _TIMELINE_NOT_FOUND(NOT_FOUND, "Timeline4040", "해당 타임라인을 찾을 수 없습니다."),
    _MAINTIMELINE_NOT_FOUND(NOT_FOUND, "MAINTIMELINE_4040", "해당 메인타임라인을 찾을 수 없습니다."),
    _ACCESS_DENIED(FORBIDDEN, "Timeline4030", "접근이 거부되었습니다."),

    // ------------------------------------------ COMMENT ------------------------------------------
    _COMMENT_NOT_FOUND(NOT_FOUND, "COMMENT_4040", "해당 댓글을 찾을 수 없습니다."),
    _COMMENT_LIKE_FAILED(BAD_REQUEST, "COMMENT_4001", "댓글 좋아요 처리에 실패했습니다."),
    _COMMENT_UNLIKE_FAILED(BAD_REQUEST, "COMMENT_4002", "댓글 좋아요 해제에 실패했습니다."),
    _COMMENT_PERMISSION_DENIED(FORBIDDEN, "COMMENT_4030", "댓글에 대한 접근 권한이 없습니다."),
    _PARENT_COMMENT_NOT_FOUND (OK,"PARENT_COMMENT_4004" , "부모 댓글을 찾을 수 없습니다."),

    // ------------------------------------------ REPLY ------------------------------------------
    _REPLY_NOT_FOUND(NOT_FOUND, "REPLY_4040", "해당 대댓글을 찾을 수 없습니다."),
    _REPLY_LIKE_FAILED(BAD_REQUEST, "REPLY_4001", "대댓글 좋아요 처리에 실패했습니다."),
    _REPLY_UNLIKE_FAILED(BAD_REQUEST, "REPLY_4002", "대댓글 좋아요 해제에 실패했습니다."),
    _REPLY_PERMISSION_DENIED(FORBIDDEN, "REPLY_4030", "대댓글에 대한 접근 권한이 없습니다."),


    // ------------------------------------------ Recommend ------------------------------------------
    _NO_RECOMMENDER_FOUND(OK, "RECOMMEND4040", "해당 관심사를 가진 사용자가 없습니다."),

    // ------------------------------------------ Search ------------------------------------------
    _NO_SEARCH_RESULTS(OK, "SEARCH4040", "검색 결과가 없습니다."),

    // ------------------------------------------ DATABASE ------------------------------------------
    _DATABASE_ERROR(OK, "DATABASE5050", "데이터베이스 오류입니다."),

    // ------------------------------------------ FOLLOW ------------------------------------------
    _FOLLOW_NOT_FOUND(OK, "FOLLOW4000", "팔로우를 찾을 수 없습니다."),
    _ALREADY_FOLLOWING(OK, "FOLLOW40001", "이미 팔로우되어 있습니다."),

    // ------------------------------------------ MAIL ------------------------------------------

    _MAIL_SEND_FAIL(OK, "MAIL5000", "메일 전송에 실패했습니다."),

    // ------------------------------------------  ------------------------------------------

    _CERTIFICATION_FAILED(OK, "CERTIFICATION4000", "인증에 실패했습니다."),
    _CERTIFICATION_NOT_VERIFIED(OK, "CERTIFICATION4001", "인증이 확인되지 않았습니다."), 
    
    
    _CAREER_DETAIL_NOT_FOUND(OK, "CAREER4000", "경력사항을 찾을 수 없습니다."),

    _NOTIFICATION_NOT_FOUND(OK, "NOTIFICATION4000", "알림이 없습니다.");


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
