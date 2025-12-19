package com.riya.rms.repositories;

import com.riya.rms.models.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {
    private final Connection con;

    public SubjectRepository(Connection con) {
        this.con = con;
    }

    public List<Subject> findByTeacherId(int teacherId) {

        String sql = """
                    SELECT
                        sub.id,
                        sub.name,
                        sub.code,
                
                        s.name   AS semester_name,
                        c.code   AS course_code
                    FROM subjects sub
                    JOIN semesters s ON s.id = sub.semester_id
                    JOIN courses c   ON c.id = s.course_id
                    WHERE sub.assigned_teacher_id = ?
                    ORDER BY c.id, s.id, sub.id
                """;

        List<Subject> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, teacherId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject sub = new Subject();
                    sub.setId(rs.getInt("id"));
                    sub.setName(rs.getString("name"));
                    sub.setCode(rs.getString("code"));

                    // derived fields
                    sub.setSemesterName(rs.getString("semester_name"));
                    sub.setCourseName(rs.getString("course_code"));

                    list.add(sub);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Create new subject
    public void create(Subject subject) throws Exception {
        String sql = """
                    INSERT INTO subjects
                    (name, code, semester_id, assigned_teacher_id)
                    VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject.getName());
            ps.setString(2, subject.getCode());
            ps.setInt(3, subject.getSemesterId());

            if (subject.getAssignedTeacherId() != null) {
                ps.setInt(4, subject.getAssignedTeacherId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();
        }
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

    public Subject findByCode(String code) throws Exception {
        String sql = "SELECT * FROM subjects WHERE code = ? LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Subject s = new Subject();
                s.setId(rs.getInt("id"));
                s.setCode(rs.getString("code"));
                s.setName(rs.getString("name"));
                s.setAssignedTeacherId(rs.getInt("assigned_teacher_id"));
                s.setSemesterId(rs.getInt("semester_id"));

                return s;
            } else {
                return null;
            }
        }
    }

    public List<Subject> findAll() {

        String sql = """
                     SELECT
                         s.id,
                         s.code,
                         s.name,
                         s.semester_id,
                         s.assigned_teacher_id,
                         c.name AS course_name,
                         sem.name AS semester_name,
                         u.name AS teacher_name
                     FROM subjects s
                     JOIN semesters sem ON s.semester_id = sem.id
                     JOIN courses c ON sem.course_id = c.id
                     LEFT JOIN users u ON s.assigned_teacher_id = u.id
                     ORDER BY c.name, sem.id, s.code
                """;

        List<Subject> subjects = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Subject s = new Subject();
                s.setId(rs.getInt("id"));
                s.setCode(rs.getString("code"));
                s.setName(rs.getString("name"));
                s.setSemesterId(rs.getInt("semester_id"));
                s.setAssignedTeacherId(
                        (Integer) rs.getObject("assigned_teacher_id")
                );

                s.setCourseName(rs.getString("course_name"));
                s.setSemesterName(rs.getString("semester_name"));
                s.setAssignedTeacherName(
                        rs.getString("teacher_name") != null
                                ? rs.getString("teacher_name")
                                : "Not Assigned"
                );

                subjects.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subjects;
    }

}
