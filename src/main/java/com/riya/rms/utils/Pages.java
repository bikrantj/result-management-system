package com.riya.rms.utils;

public enum Pages {
    INDEX("index.jsp"),
    ADMIN_DASHBOARD("/admin/dashboard.jsp"),
    TEACHER_DASHBOARD("/teacher/dashboard.jsp"),
    STUDENT_DASHBOARD("/student/dashboard.jsp");

    private final String path;

    Pages(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
