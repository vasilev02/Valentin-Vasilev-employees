package org.sirma.operation;

import org.sirma.model.ProjectInformation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.sirma.constants.Constants.DATE_FORMATS;
import static org.sirma.constants.ExceptionMessages.*;

/**
 * ProjectOperations class contains all the necessary logic behind reading a csv file and parsing data.
 */
public class ProjectOperations {

    private ProjectOperations() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Read CSV file by providing a file path. The logic is simple by go through all lines from the given CSV file
     * and parse it to our ProjectInformation class.
     *
     * @throws IllegalArgumentException exception if there is uncorrected data provided.
     * @throws NoSuchElementException exception if there is empty file provided.
     * @param String filePath - path to the csv file
     * @return list of ProjectInformation class.
     */
    public static List<ProjectInformation> readCSV(String filePath) {
        List<ProjectInformation> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    throw new IllegalArgumentException(CORRECT_DATA_EXCEPTION);
                }
                String partZero = parts[0].trim();
                int empID = Integer.parseInt(partZero);
                int projectID = Integer.parseInt(parts[1].trim());
                Date dateFrom = parseDate(parts[2].trim());
                Date dateTo = parts[3].trim().equalsIgnoreCase("NULL") ? new Date() : parseDate(parts[3].trim());
                data.add(new ProjectInformation(empID, projectID, dateFrom, dateTo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data.isEmpty()) {
            throw new NoSuchElementException(EMPTY_FILE_EXCEPTION);
        }
        return data;
    }

    /**
     * Parses text from the given string to produce a date. We are trying to parse from ouy DATE_FORMATS constant which
     * contains list of Date formats.
     *
     * @return Date parsed date
     * @throws IllegalArgumentException exception if there is not achieved any parsing from our constants.
     */
    public static Date parseDate(String dateString) throws IllegalArgumentException {
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                return dateFormat.parse(dateString);
            } catch (ParseException ignored) {
            }
        }
        throw new IllegalArgumentException(UNABLE_TO_PARSE_DATE_EXCEPTION + dateString);
    }

}
