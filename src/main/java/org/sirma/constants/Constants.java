package org.sirma.constants;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
    public static final String APP_TITLE = "Employee Analyzer";
    public static final int APP_WIDTH = 1000;
    public static final int APP_HEIGHT = 600;
    public static final String BUTTON_NAME = "Upload CSV File";
    public static final String UNABLE_TO_PARSE_DATE = "Unable to parse date: ";
    public static final String[] DATE_FORMATS = {"yyyy-MM-dd", "dd/MM/yyyy", "MM/dd/yyyy"};
    public static final String COLUMN_EMPLOYEE_1 = "Employee ID #1";
    public static final String COLUMN_EMPLOYEE_2 = "Employee ID #2";
    public static final String PROJECT_ID = "Project ID";
    public static final String TIME_SPENT = "Time spent";
}
