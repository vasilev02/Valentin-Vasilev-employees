package org.sirma.model;

import java.util.Map;

public class Pair {
    private int empID1;
    private int empID2;
    private int workingTime;
    private Map<Integer, Integer> projects;

    public Pair(int empID1, int empID2, int workingTime, Map<Integer, Integer> projects) {
        this.empID1 = empID1;
        this.empID2 = empID2;
        this.workingTime = workingTime;
        this.projects = projects;
    }

    public int getEmpID1() {
        return empID1;
    }

    public void setEmpID1(int empID1) {
        this.empID1 = empID1;
    }

    public int getEmpID2() {
        return empID2;
    }

    public void setEmpID2(int empID2) {
        this.empID2 = empID2;
    }

    public int getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(int workingTime) {
        this.workingTime = workingTime;
    }

    public Map<Integer, Integer> getProjects() {
        return projects;
    }

    public void setProjects(Map<Integer, Integer> projects) {
        this.projects = projects;
    }
}