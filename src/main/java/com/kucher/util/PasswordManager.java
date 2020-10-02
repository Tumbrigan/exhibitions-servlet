package com.kucher.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordManager {
    public static String getPasswordHash(String password) {
        return DigestUtils.sha256Hex(password);
    }
}
