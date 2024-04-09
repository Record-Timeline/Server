package com.api.RecordTimeline.domain.common;

public interface ResponseMessage {

    String SUCCESS = "Success";
    String VALIDATION_FAIL = "유효성 검사를 실패했습니다";
    String DUPLICATE_ID = "이미 존재하는 아이디입니다.";
    String DUPLICATE_EMAIL = "이미 존재하는 이메일입니다.";
    String DUPLICATE_NICKNAME = "이미 존재하는 닉네임입니다.";
    String SIGN_IN_FAIL = "로그인에 실패했습니다.";
    String CERTIFICATION_FAILED = "인증에 실패하였습니다.";
    String MAIL_FAIL = "메일 전송에 실패했습니다.";
    String DATABASE_ERROR = "데이터베이스 오류입니다.";

}
