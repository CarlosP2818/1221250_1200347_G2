package pt.psoft.g1.psoftg1.shared.services;

import java.security.SecureRandom;
import java.util.UUID;

public class Base65IdGenerator {
    private static final String BASE65_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_+";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(BASE65_CHARS.length());
            sb.append(BASE65_CHARS.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // Exemplo: gerar ID de 12 caracteres
        String id = generateId(12);
        System.out.println("Generated ID: " + id);
    }
}
