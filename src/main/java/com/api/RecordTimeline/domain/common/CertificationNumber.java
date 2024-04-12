package com.api.RecordTimeline.domain.common;

import java.security.SecureRandom;

public class CertificationNumber {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String getCertificationNumber() {
        StringBuilder certificationNumber = new StringBuilder();
        int charactersLength = CHARACTERS.length();
        for (int i = 0; i < 6; i++) {
            int randomIndex = RANDOM.nextInt(charactersLength);
            char randomChar = CHARACTERS.charAt(randomIndex);
            certificationNumber.append(randomChar);
        }
        return certificationNumber.toString();
    }
}
