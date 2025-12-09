package com.riya.rms.utils;

public enum Pages {
    INDEX("index.jsp"),
    ADMIN_DASHBOARD("/admin/dashboard.jsp"),
    ADMIN_COURSE("/admin/courses"),
    ADMIN_NEW_COURSE("/admin/courses/new-course.jsp"),
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
