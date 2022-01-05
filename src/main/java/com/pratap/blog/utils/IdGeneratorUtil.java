package com.pratap.blog.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class IdGeneratorUtil {

    private static final Random RANDOM = new SecureRandom();
    public static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generateId(int length) {
        return generateRandomString(length);
    }

    private static String generateRandomString(int length) {

        StringBuilder returnVal = new StringBuilder(length);

        for(int i = 0; i < length; i++) {
            returnVal.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnVal);
    }
}
