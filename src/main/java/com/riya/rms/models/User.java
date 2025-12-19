package com.riya.rms.models;

import com.riya.rms.utils.Role;

public class User {

    private int id;
    private String username;
    private String password;
    private String name;
    private Role role;
    private Course course;
    private Semester semester;
    private Subject subject;

    public User() {
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course courseName) {
        this.course = courseName;
    }
}
