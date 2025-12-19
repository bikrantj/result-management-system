package com.riya.rms.repositories;

import com.riya.rms.models.Exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamRepository {

    private final Connection con;

    public ExamRepository(Connection con) {
        this.con = con;
    }

    public void create(Exam exam) throws SQLException {
        String sql = """
                    INSERT INTO exams (name, course_id, semester_id, full_marks)
                    VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, exam.getName());
            ps.setInt(2, exam.getCourseId());
            ps.setInt(3, exam.getSemesterId());
            ps.setInt(4, exam.getFullMarks());
            ps.executeUpdate();
        }
    }

    public List<Exam> findAll() {
        String sql = """
                    SELECT
                        e.id,
                        e.name,
                        e.full_marks,
                
                        c.id AS course_id,
                        c.name AS course_name,
                
                        s.id AS semester_id,
                        s.name AS semester_name
                    FROM exams e
                    JOIN courses c ON c.id = e.course_id
                    JOIN semesters s ON s.id = e.semester_id
                    ORDER BY e.created_at DESC
                """;

        List<Exam> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Exam e = new Exam();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setFullMarks(rs.getInt("full_marks"));

                e.setCourseId(rs.getInt("course_id"));
                e.setCourseName(rs.getString("course_name"));

                e.setSemesterId(rs.getInt("semester_id"));
                e.setSemesterName(rs.getString("semester_name"));

                list.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
