package com.api.RecordTimeline.domain.common;

public interface ResponseMessage {

    String SUCCESS = "Success";
    String VALIDATION_FAIL = "유효성 검사를 실패했습니다";
    String DUPLICATE_EMAIL = "이미 존재하는 이메일입니다.";
    String SIGN_IN_FAIL = "로그인에 실패했습니다.";
    String DATABASE_ERROR = "데이터베이스 오류입니다.";

}
