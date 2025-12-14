package com.riya.rms.utils;

public enum Pages {
    INDEX("index.jsp"),


    TEACHER_DASHBOARD("/WEB-INF/teacher/dashboard.jsp"),
    STUDENT_DASHBOARD("/WEB-INF/student/dashboard.jsp"),


    ADMIN_DASHBOARD("/WEB-INF/admin/dashboard.jsp"),
    ADMIN_NEW_COURSE("/WEB-INF/admin/courses/new-course.jsp"),
    ADMIN_NEW_SUBJECT("/WEB-INF/admin/subjects/add-new-subject.jsp"),
    ADMIN_COURSE("/WEB-INF/admin/courses/index.jsp"),
    ADMIN_CREATE_NEW_USER("/WEB-INF/admin/create-new-user.jsp"),
    ADMIN_SUBJECTS("/WEB-INF/admin/subjects/index.jsp"),
    ADMIN_STUDENT("/WEB-INF/admin/students/index.jsp");

    private final String path;

    Pages(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
