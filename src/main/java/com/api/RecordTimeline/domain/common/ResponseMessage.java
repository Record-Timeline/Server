package com.api.RecordTimeline.domain.common;

public interface ResponseMessage {

    String SUCCESS = "Success";
    String MEMBER_NOT_FOUND = "사용자를 찾을 수 없습니다.";
    String VALIDATION_FAIL = "검증에 실패했습니다";
    String DUPLICATE_EMAIL = "이미 존재하는 이메일입니다.";
    String DUPLICATE_NICKNAME = "이미 존재하는 닉네임입니다.";
    String INVALID_PASSWORD_PATTERN = "비밀번호 형식에 맞지 않습니다.";
    String APP_LOGIN_FAIL = "로그인에 실패했습니다.";
    String CERTIFICATION_FAILED = "이메일 인증번호가 유효하지 않습니다.";
    String PASSWORD_MISMATCH = "비밀번호가 틀립니다.";
    String NOT_AUTHORIZED = "권한이 없습니다.";
    String MEMBER_DELETED_FAELED = "회원 탈퇴에 실패했습니다.";
    String MAIL_FAIL = "메일 전송에 실패했습니다.";
    String NO_IMAGE_FOUND = "이미지를 찾을 수 없습니다.";
    String UPDATE_FAILED = "업데이트에 실패했습니다.";
    String IMAGE_DELETED = "이미지가 삭제되었습니다.";
    String INTRODUCTION_CLEARED = "소개글을 지웠습니다.";
    String NO_PROFILE_FOUND = "사용자의 프로필이 존재하지 않습니다.";

    String DATABASE_ERROR = "데이터베이스 오류입니다.";

}
