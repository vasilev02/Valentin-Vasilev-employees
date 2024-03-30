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

import static org.sirma.constants.Constants.DATE_FORMATS;
import static org.sirma.constants.Constants.UNABLE_TO_PARSE_DATE;

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
     * @param String filePath - path to the csv file
     * @return list of ProjectInformation class.
     */
    public static List<ProjectInformation> readCSV(String filePath) {
        List<ProjectInformation> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
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
        return data;
    }

    /**
     * Parses text from the given string to produce a date. We are trying to parse from ouy DATE_FORMATS constant which
     * contains list of Date formats.
     *
     * @throws IllegalArgumentException exception if there is not achieved any parsing from our constants.
     * @return Date parsed date
     */
    private static Date parseDate(String dateString) throws IllegalArgumentException {
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                return dateFormat.parse(dateString);
            } catch (ParseException ignored) {
            }
        }
        throw new IllegalArgumentException(UNABLE_TO_PARSE_DATE + dateString);
    }

}
