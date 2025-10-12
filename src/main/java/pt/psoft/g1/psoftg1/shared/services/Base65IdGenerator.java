package pt.psoft.g1.psoftg1.shared.services;

import java.security.SecureRandom;
import java.util.UUID;

public class Base65IdGenerator {
    private static final String BASE65_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_+";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int DEFAULT_LENGTH = 5;

    public static String generateId() {
        StringBuilder sb = new StringBuilder(DEFAULT_LENGTH);
        for (int i = 0; i < DEFAULT_LENGTH; i++) {
            int index = RANDOM.nextInt(BASE65_CHARS.length());
            sb.append(BASE65_CHARS.charAt(index));
        }
        return sb.toString();
    }
}
