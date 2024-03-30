package org.sirma;

import org.sirma.model.Pair;
import org.sirma.model.ProjectInformation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

import static org.sirma.constants.Constants.*;
import static org.sirma.operation.ProjectOperations.readCSV;

/**
 * EmployeeAnalyzerUI class is the main class which is using all the code's logic. Inside of it we are creating by
 * Java Swing API simple UI panel where we can upload our CSV file and see the desired results by data table and text box.
 */
public class EmployeeAnalyzerUI extends JFrame {

    private final JTable dataTable;
    private final JTextArea textArea;

    /**
     * EmployeeAnalyzerUI is used to compose as little blocks the entire UI by some main containers in Swing API.
     */
    public EmployeeAnalyzerUI() {
        setTitle(APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(APP_WIDTH, APP_HEIGHT);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();

        JButton openButton = new JButton(BUTTON_NAME);
        openButton.addActionListener(new OpenButtonListener());
        controlPanel.add(openButton);

        dataTable = new JTable();
        add(new JScrollPane(dataTable), BorderLayout.CENTER);

        textArea = new JTextArea(15, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.SOUTH);

        add(controlPanel, BorderLayout.NORTH);
    }

    /**
     * OpenButtonListener class which listen for the uploading of csv files and printing the result on the UI.
     *
     * @implements ActionListener interface the listener interface for receiving action events
     */
    private class OpenButtonListener implements ActionListener {

        /**
         * Listening for event for uploading CSV file from UI. Print the result in a text area container on UI.
         */
        public void actionPerformed(ActionEvent e) {
            StringBuilder stringBuilder = new StringBuilder();
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                List<ProjectInformation> projectDataList;
                try {
                    projectDataList = readCSV(selectedFile.getAbsolutePath());
                }catch (IllegalArgumentException | NoSuchElementException exception){
                    stringBuilder.append(exception.getMessage());
                    textArea.setText(stringBuilder.toString());
                    return;
                }

                DefaultTableModel model = getTableModel(projectDataList);
                dataTable.setModel(model);

                Pair pair = findLongestWorkingPair(projectDataList);
                stringBuilder.append("Longest working pair of employees.\n\n");
                stringBuilder.append("Employee ID #1: ").append(pair.getEmpID1()).append("\n");
                stringBuilder.append("Employee ID #2: ").append(pair.getEmpID2()).append("\n\n");

                int workingDays = pair.getWorkingTime();
                int workingWeeks = workingDays / 7;
                int workingMonths = workingDays / 30;
                int workingYears = workingDays / 365;

                stringBuilder.append("They worked together on common projects for: ").append(workingDays).append(" days.").append("\n");

                stringBuilder.append("Converted: ")
                        .append(workingWeeks).append(" weeks/ ")
                        .append(workingMonths).append(" months/ ")
                        .append(workingYears).append(" years.")
                        .append("\n\n");

                stringBuilder.append("Total count of their common projects: ").append(pair.getProjects().size()).append(".\n\n");

                stringBuilder.append("Projects sorted by ID.\n");
                pair.getProjects().forEach((key, value) -> stringBuilder.append(" - Project with ID: ").append(key).append(" time spent: ")
                        .append(value).append(" days.").append("\n"));

                textArea.setText(stringBuilder.toString());
            }
        }
    }

    /**
     * Creating the table by giving it first the header names and fulfill it with rows from the pair of employees.
     * But before that we fetch the longest working fair.
     *
     * @param List of ProjectInformation projectDataList
     * @return DefaultTableModel model
     */
    private DefaultTableModel getTableModel(List<ProjectInformation> projectDataList) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(COLUMN_EMPLOYEE_1);
        model.addColumn(COLUMN_EMPLOYEE_2);
        model.addColumn(PROJECT_ID);
        model.addColumn(TIME_SPENT);

        Pair pair = findLongestWorkingPair(projectDataList);

        pair.getProjects().forEach((key, value) -> model.addRow(new Object[]{pair.getEmpID1(),pair.getEmpID2(),key, value}));
        return model;
    }

    /**
     * Get the pair of two employees which have worked the longest time on common projects.
     *
     * @param List of ProjectInformation projectDataList
     * @return Pair pair - longest working pair
     */
    private static Pair findLongestWorkingPair(List<ProjectInformation> projectDataList) {
        Map<Pair, Integer> workingPeriods = new HashMap<>();
        for (int i = 0; i < projectDataList.size(); i++) {
            for (int j = i + 1; j < projectDataList.size(); j++) {

                ProjectInformation pd1 = projectDataList.get(i);
                ProjectInformation pd2 = projectDataList.get(j);

                if (pd1.getProjectID() == pd2.getProjectID() && pd1.getEmpID() != pd2.getEmpID()) {
                    int newTime = computeWorkingTime(pd1, pd2);

                    if (workingPeriods.isEmpty()) {
                        Pair newPair = createPair(pd1, pd2, newTime);
                        workingPeriods.put(newPair, newTime);
                    } else {
                        Pair updatedPair = updatePair(pd1, pd2, workingPeriods, newTime);
                        if (updatedPair != null) {
                            workingPeriods.put(updatedPair, updatedPair.getWorkingTime());
                        } else {
                            Pair newPair = createPair(pd1, pd2, newTime);
                            workingPeriods.put(newPair, newTime);
                        }
                    }
                }
            }
        }
        return Collections.max(workingPeriods.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /**
     * Compute the working time by the given date from each employee. We can easily change by our requirements if we
     * want to calculate it in days or weeks etc.. by delete or add the value for the period.
     *
     * @param ProjectInformation pd1 - first employee
     * @param ProjectInformation pd2 - second employee
     * @return int time in DAYS
     */
    private static int computeWorkingTime(ProjectInformation pd1, ProjectInformation pd2) {
        Date startDate = pd1.getDateFrom().after(pd2.getDateFrom()) ? pd1.getDateFrom() : pd2.getDateFrom();
        Date endDate = pd1.getDateTo().before(pd2.getDateTo()) ? pd1.getDateTo() : pd2.getDateTo();
        long diff = endDate.getTime() - startDate.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    /**
     * Creating pair between two employees.
     *
     * @param ProjectInformation firstEmp - first employee
     * @param ProjectInformation secondEmp - second employee
     * @param int newTime - time to be set for the first time
     * @return Pair newly created pair
     */
    private static Pair createPair(ProjectInformation firstEmp, ProjectInformation secondEmp, int newTime) {
        Map<Integer, Integer> projects = new TreeMap<>();
        projects.put(firstEmp.getProjectID(), newTime);
        return new Pair(firstEmp.getEmpID(), secondEmp.getEmpID(), newTime, projects);
    }

    /**
     * Creating pair between two employees.
     *
     * @param ProjectInformation firstEmp - first employee
     * @param ProjectInformation secondEmp - second employee
     * @param Map workingPeriods - our collection where we store the results
     * @param int newTime - time to be set for the first time
     * @return Pair if there is such to update or null
     */
    private static Pair updatePair(ProjectInformation firstEmp, ProjectInformation secondEmp, Map<Pair, Integer> workingPeriods, int newTime) {

        for (Map.Entry<Pair, Integer> entry : workingPeriods.entrySet()) {
            Pair pair = entry.getKey();

            if (pair.getEmpID1() == firstEmp.getEmpID() || pair.getEmpID1() == secondEmp.getEmpID()
                    && pair.getEmpID2() == firstEmp.getEmpID() || pair.getEmpID2() == secondEmp.getEmpID()) {

                if (pair.getProjects().containsKey(firstEmp.getProjectID())) {
                    Integer currentTime = pair.getProjects().get(firstEmp.getProjectID());
                    pair.getProjects().put(firstEmp.getProjectID(), currentTime + newTime);
                } else {
                    pair.getProjects().put(firstEmp.getProjectID(), newTime);
                }
                pair.setWorkingTime(pair.getWorkingTime() + newTime);
                return pair;
            }
        }
        return null;
    }

    /**
     * Our main starting point.
     *
     * @param Arrays args
     * @return void
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeAnalyzerUI ui = new EmployeeAnalyzerUI();
            ui.setVisible(true);
        });
    }
}
