package org.sirma;

import org.junit.jupiter.api.Test;
import org.sirma.model.Pair;
import org.sirma.model.ProjectInformation;

import static org.junit.jupiter.api.Assertions.*;
import static org.sirma.EmployeeAnalyzerUI.computeWorkingTime;
import static org.sirma.EmployeeAnalyzerUI.findLongestWorkingPair;
import static org.sirma.operation.ProjectOperations.parseDate;
import static org.sirma.operation.ProjectOperations.readCSV;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * EmployeeAnalyzerUITest is a test class from which we try to validate our business logic if it is working correctly.
 */
public class EmployeeAnalyzerUITest {

    @Test
    public void testReadCSV() {
        String filePath = "src/test/resources/testData.csv";
        List<ProjectInformation> projectDataList = readCSV(filePath);

        assertNotNull(projectDataList);
        assertFalse(projectDataList.isEmpty());
        assertEquals(3, projectDataList.size());
    }

    @Test
    public void testComputeWorkingTime() {
        ProjectInformation pd1 = new ProjectInformation(1, 100, parseDate("2022-01-01"), parseDate("2022-01-10"));
        ProjectInformation pd2 = new ProjectInformation(2, 100, parseDate("2022-01-05"), parseDate("2022-01-15"));

        int expectedWorkingDays = 5;

        int actualWorkingDays = computeWorkingTime(pd1, pd2);
        assertEquals(expectedWorkingDays, actualWorkingDays);
    }

    @Test
    public void testFindLongestWorkingPair() {
        List<ProjectInformation> projectDataList = createTestProjectDataList();

        Pair longestWorkingPair = findLongestWorkingPair(projectDataList);

        assertNotNull(longestWorkingPair);
        assertEquals(1, longestWorkingPair.getEmpID1());
        assertEquals(2, longestWorkingPair.getEmpID2());
        assertEquals(5, longestWorkingPair.getProjects().get(101));
    }

    private List<ProjectInformation> createTestProjectDataList() {
        ProjectInformation pd1 = new ProjectInformation(1, 100, parseDate("2022-01-01"), parseDate("2022-01-10"));
        ProjectInformation pd2 = new ProjectInformation(1, 101, parseDate("2022-01-05"), parseDate("2022-01-15"));
        ProjectInformation pd3 = new ProjectInformation(2, 100, parseDate("2022-01-03"), parseDate("2022-01-13"));
        ProjectInformation pd4 = new ProjectInformation(2, 101, parseDate("2022-01-10"), parseDate("2022-01-20"));
        return List.of(pd1, pd2, pd3, pd4);
    }

    @Test
    public void testWithInvalidDate() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectInformation(1, 100, parseDate("2022-01-"), parseDate("2022-01-10")));
    }

    @Test
    public void testWithInvalidCountOfParameters() {
        String filePath = "src/test/resources/testIncorrectData.csv";
        assertThrows(IllegalArgumentException.class, () -> {
            List<ProjectInformation> projectDataList = readCSV(filePath);
        });
    }

    @Test
    public void testWithEmptyCSVFile() {
        String filePath = "src/test/resources/testEmptyData.csv";
        assertThrows(NoSuchElementException.class, () -> {
            List<ProjectInformation> projectDataList = readCSV(filePath);
        });
    }
}
