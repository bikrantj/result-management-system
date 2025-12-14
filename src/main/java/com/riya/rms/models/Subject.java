package com.riya.rms.models;

public class Subject {

    // --- Core DB fields ---
    private int id;
    private String name;
    private String code;
    private int semesterId;
    private Integer assignedTeacherId; // nullable

    // --- Derived / joined fields (read-only usage) ---
    private String courseName;
    private String semesterName;
    private String assignedTeacherName;

    // --- Getters & setters ---
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

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public Integer getAssignedTeacherId() {
        return assignedTeacherId;
    }

    public void setAssignedTeacherId(Integer assignedTeacherId) {
        this.assignedTeacherId = assignedTeacherId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getAssignedTeacherName() {
        return assignedTeacherName;
    }

    public void setAssignedTeacherName(String assignedTeacherName) {
        this.assignedTeacherName = assignedTeacherName;
    }
}
