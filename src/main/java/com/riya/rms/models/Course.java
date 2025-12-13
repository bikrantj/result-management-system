package com.riya.rms.models;


import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private String name;
    private String code;
    private int semesterCount;
    private int totalStudents;
    private List<Semester> semesters = new ArrayList<>();

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getSemesterCount() {
        return semesterCount;
    }

    public void setSemesterCount(int semesterCount) {
        this.semesterCount = semesterCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
