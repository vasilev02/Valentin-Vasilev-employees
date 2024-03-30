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

public class ProjectOperations {

    private ProjectOperations() {
        throw new IllegalStateException("Utility class");
    }

    public static List<ProjectInformation> readCSV(String filename) {
        List<ProjectInformation> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
