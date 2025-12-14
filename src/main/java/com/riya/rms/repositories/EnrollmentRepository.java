package com.riya.rms.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class EnrollmentRepository {
    private final Connection con;

    public EnrollmentRepository(Connection con) {
        this.con = con;
    }

    public void createOrUpdateEnrollment(int studentId, int courseId, int semesterId)
            throws Exception {

        String sql = """
                    INSERT INTO student_enrollments (student_id, course_id, current_semester_id)
                    VALUES (?, ?, ?)
                    ON DUPLICATE KEY UPDATE
                        course_id = VALUES(course_id),
                        current_semester_id = VALUES(current_semester_id)
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setInt(3, semesterId);
            ps.executeUpdate();
        }
    }

}
