package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.persistence.mongo;

import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class IsbnMongo implements Serializable {
    @Size(min = 10, max = 13)
    private String isbn;

    public IsbnMongo(String isbn) {
        if (isValidIsbn(isbn)) {
            this.isbn = isbn;
        } else {
            throw new IllegalArgumentException("Invalid ISBN-10 or ISBN-13 format.");
        }
    }

    protected IsbnMongo() {
    }

    private static boolean isValidIsbn(String isbn) {
        if (isbn == null)
            throw new IllegalArgumentException("Isbn cannot be null");
        return (isbn.length() == 10) ? isValidIsbn10(isbn) : isValidIsbn13(isbn);
    }

    private static boolean isValidIsbn10(String isbn) {
        if (!isbn.matches("\\d{9}[\\dX]")) return false;

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }
        int lastDigit = (isbn.charAt(9) == 'X') ? 10 : isbn.charAt(9) - '0';
        sum += lastDigit;
        return sum % 11 == 0;
    }

    private static boolean isValidIsbn13(String isbn) {
        if (!isbn.matches("\\d{13}")) return false;

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = 10 - (sum % 10);
        if (checksum == 10) checksum = 0;
        return checksum == (isbn.charAt(12) - '0');
    }

    @Override
    public String toString() {
        return this.isbn;
    }
}
