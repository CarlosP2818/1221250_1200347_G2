package pt.psoft.g1.psoftg1.readermanagement.infraestructure.persistence.mongo;

import lombok.Getter;

import java.time.LocalDate;

public class BirthDateMongo {

    @Getter
    LocalDate birthDate;

    private final String dateFormatRegexPattern = "\\d{4}-\\d{2}-\\d{2}";

    private int minimumAge = 18;

    public BirthDateMongo(int year, int month, int day) {
        setBirthDate(year, month, day);
    }

    public BirthDateMongo(String birthDate) {
        if (!birthDate.matches(dateFormatRegexPattern)) {
            throw new IllegalArgumentException("Provided birth date is not in a valid format. Use yyyy-MM-dd");
        }

        String[] dateParts = birthDate.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        setBirthDate(year, month, day);
    }

    private void setBirthDate(int year, int month, int day) {
        this.birthDate = LocalDate.of(year, month, day);
    }

}