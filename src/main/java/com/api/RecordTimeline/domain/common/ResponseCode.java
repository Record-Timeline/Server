package com.api.RecordTimeline.domain.common;

public interface ResponseCode {
    String SUCCESS = "SU";
    String VALIDATION_FAIL = "VF";
    String DUPLICATE_ID = "DI";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_NICKNAME = "DN";
    String SIGN_IN_FAIL = "CF";

    String MAIL_FAIL = "MF";
    String DATABASE_ERROR = "DBE";
}
