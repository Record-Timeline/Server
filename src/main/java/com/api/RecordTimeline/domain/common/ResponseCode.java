package com.api.RecordTimeline.domain.common;

public interface ResponseCode {
    String SUCCESS = "SU";
    String MEMBER_NOT_FOUND = "MNF";
    String VALIDATION_FAIL = "VF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_NICKNAME = "DN";
    String INVALID_PASSWORD_PATTERN = "IPP";
    String APP_LOGIN_FAIL = "ALF";
    String CERTIFICATION_FAILED = "CF";
    String PASSWORD_MISMATCH = "PM";
    String NOT_AUTHORIZED = "NA";

    String MEMBER_DELETED_FAELED = "MDF";

    String MAIL_FAIL = "MF";
    String UPDATE_FAILED = "UF";

    String NO_IMAGE_FOUND = "NIF";
    String NO_PROFILE_FOUND = "NPF";
    String DATABASE_ERROR = "DBE";
}
