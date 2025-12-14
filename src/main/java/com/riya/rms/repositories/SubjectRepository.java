package com.riya.rms.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SubjectRepository {
    private final Connection con;

    public SubjectRepository(Connection con) {
        this.con = con;
    }

    public void updateAssignedTeacher(int subjectId, int teacherId)
            throws Exception {

        String sql = """
                    UPDATE subjects
                    SET assigned_teacher_id = ?
                    WHERE id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ps.setInt(2, subjectId);
            ps.executeUpdate();
        }
    }
}
