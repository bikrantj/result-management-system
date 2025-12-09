package com.riya.rms.utils;

public enum Pages {
    INDEX("index.jsp"),
    ADMIN_DASHBOARD("/WEB-INF/admin/dashboard.jsp"),
    ADMIN_COURSE("/WEB-INF/admin/courses/index.jsp"),
    ADMIN_NEW_COURSE("/WEB-INF/admin/courses/new-course.jsp"),
    TEACHER_DASHBOARD("/WEB-INF/teacher/dashboard.jsp"),
    STUDENT_DASHBOARD("/WEB-INF/student/dashboard.jsp");

    private final String path;

    Pages(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
