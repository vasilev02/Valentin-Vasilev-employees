package org.sirma.constants;

public class ExceptionMessages {

    private ExceptionMessages() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CORRECT_DATA_EXCEPTION = "Please, provide correct data in the csv file.";
    public static final String EMPTY_FILE_EXCEPTION = "Provide not empty file!";
    public static final String UNABLE_TO_PARSE_DATE_EXCEPTION = "Unable to parse date: ";
}
