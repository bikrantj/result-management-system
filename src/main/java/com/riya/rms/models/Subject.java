package com.riya.rms.models;

public class Subject {
    private int id;
    private String name;
    private String code;
    private int semesterId;
    private int assignedTeacherId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getSemesterId() { return semesterId; }
    public void setSemesterId(int semesterId) { this.semesterId = semesterId; }

    public int getAssignedTeacherId() { return assignedTeacherId; }
    public void setAssignedTeacherId(int assignedTeacherId) { this.assignedTeacherId = assignedTeacherId; }
}