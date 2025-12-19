package com.riya.rms.repositories;

import com.riya.rms.models.Exam;
import com.riya.rms.models.StudentMarkDTO;
import com.riya.rms.models.StudentSubjectMarkDTO;

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

    public Exam findById(int examId, int teacherId) {

        String sql = """
                    SELECT e.id, e.name, e.full_marks,
                           e.course_id, e.semester_id,
                           c.name AS course_name,
                           c.code AS course_code,
                           s.name AS semester_name,
                           sub.name AS subject_name
                    FROM exams e
                    JOIN courses c ON c.id = e.course_id
                    JOIN semesters s ON s.id = e.semester_id
                    JOIN subjects sub 
                        ON sub.semester_id = e.semester_id
                       AND sub.assigned_teacher_id = ?
                    WHERE e.id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ps.setInt(2, examId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Exam e = new Exam();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setFullMarks(rs.getInt("full_marks"));
                e.setCourseId(rs.getInt("course_id"));
                e.setSemesterId(rs.getInt("semester_id"));
                e.setCourseName(rs.getString("course_name"));
                e.setCourseCode(rs.getString("course_code"));
                e.setSemesterName(rs.getString("semester_name"));
                e.setSubjectName(rs.getString("subject_name")); // add field
                return e;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public List<Exam> findAll() {
        String sql = """
                SELECT
                    e.id,
                    e.name,
                    e.full_marks,
                    e.is_published,
                
                    c.id AS course_id,
                    c.name AS course_name,
                    c.code AS course_code,
                
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
                e.setPublished(rs.getBoolean("is_published"));

                e.setCourseId(rs.getInt("course_id"));
                e.setCourseName(rs.getString("course_name"));
                e.setCourseCode(rs.getString("course_code"));  // New

                e.setSemesterId(rs.getInt("semester_id"));
                e.setSemesterName(rs.getString("semester_name"));

                // subjectName remains null here
                e.setSubjectName(null);

                list.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Exam> findAllForTeacher(int teacherId) {

        String sql = """
                    SELECT DISTINCT e.id, e.name, e.course_id, e.semester_id, e.full_marks,
                                    c.name AS course_name, c.code AS course_code,
                                    s.name AS semester_name
                    FROM exams e
                    JOIN courses c ON c.id = e.course_id
                    JOIN semesters s ON s.id = e.semester_id
                    JOIN subjects sub ON sub.semester_id = e.semester_id
                    WHERE sub.assigned_teacher_id = ?
                    ORDER BY e.created_at DESC
                """;

        List<Exam> exams = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, teacherId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Exam e = new Exam();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setCourseId(rs.getInt("course_id"));
                e.setSemesterId(rs.getInt("semester_id"));
                e.setFullMarks(rs.getInt("full_marks"));
                e.setCourseName(rs.getString("course_name"));
                e.setCourseCode(rs.getString("course_code"));
                e.setSemesterName(rs.getString("semester_name"));
                exams.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return exams;
    }

    public List<StudentMarkDTO> findStudentsForExam(int examId, int teacherId) throws SQLException {
        // First, determine the exact subject_id for this exam
        int subjectId = getSubjectId(examId, teacherId);
        if (subjectId == -1) {
            return new ArrayList<>(); // No valid subject found; return empty list safely
        }

        String sql = """
                SELECT u.id AS student_id,
                       u.name AS student_name,
                       COALESCE(m.marks_obtained, 0.0) AS marks_obtained
                FROM exams e
                JOIN student_enrollments se ON se.current_semester_id = e.semester_id
                JOIN users u ON u.id = se.student_id
                LEFT JOIN marks m 
                    ON m.exam_id = e.id 
                   AND m.student_id = u.id 
                   AND m.subject_id = ?
                WHERE e.id = ?
                  AND u.role = 'student'
                ORDER BY u.name
                """;

        List<StudentMarkDTO> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ps.setInt(2, examId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentMarkDTO dto = new StudentMarkDTO();
                    dto.setStudentId(rs.getInt("student_id"));
                    dto.setStudentName(rs.getString("student_name"));
                    // Use getDouble to properly handle DECIMAL(5,2)
                    dto.setMarks(rs.getDouble("marks_obtained"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Exam> findExamsForStudent(int studentId) throws SQLException {
        String sql = """
                SELECT DISTINCT e.id, e.name, e.full_marks, e.is_published,
                       c.name AS course_name, sem.name AS semester_name,
                       sub.name AS subject_name
                FROM exams e
                JOIN semesters sem ON e.semester_id = sem.id
                JOIN courses c ON e.course_id = c.id
                JOIN student_enrollments se ON se.current_semester_id = sem.id
                JOIN subjects sub ON sub.semester_id = sem.id
                WHERE se.student_id = ?
                ORDER BY e.created_at DESC
                """;

        List<Exam> exams = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Exam exam = new Exam();
                    exam.setId(rs.getInt("id"));
                    exam.setName(rs.getString("name"));
                    exam.setFullMarks(rs.getInt("full_marks"));
                    exam.setPublished(rs.getBoolean("is_published"));
                    exam.setCourseName(rs.getString("course_name"));
                    exam.setSemesterName(rs.getString("semester_name"));
                    exam.setSubjectName(rs.getString("subject_name"));
                    exams.add(exam);
                }
            }
        }
        return exams;
    }

    public Exam findByIdForStudent(int examId, int studentId) throws SQLException {
        String sql = """
                SELECT e.id, e.name, e.full_marks, e.is_published, e.date,
                       c.name AS course_name, sem.name AS semester_name
                FROM exams e
                JOIN semesters sem ON e.semester_id = sem.id
                JOIN courses c ON e.course_id = c.id
                JOIN student_enrollments se ON se.current_semester_id = sem.id
                WHERE e.id = ? AND se.student_id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            ps.setInt(2, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Exam exam = new Exam();
                    exam.setId(rs.getInt("id"));
                    exam.setName(rs.getString("name"));
                    exam.setFullMarks(rs.getInt("full_marks"));
                    exam.setPublished(rs.getBoolean("is_published"));
                    // set date if needed
                    exam.setCourseName(rs.getString("course_name"));
                    exam.setSemesterName(rs.getString("semester_name"));
                    return exam;
                }
            }
        }
        return null;
    }

    public List<StudentSubjectMarkDTO> findMarksForExamAndStudent(int examId, int studentId) throws SQLException {
        String sql = """
                SELECT sub.name AS subject_name,
                       e.full_marks,
                       m.marks_obtained
                FROM exams e
                JOIN semesters sem ON e.semester_id = sem.id
                JOIN subjects sub ON sub.semester_id = sem.id
                LEFT JOIN marks m ON m.exam_id = e.id 
                                 AND m.student_id = ? 
                                 AND m.subject_id = sub.id
                WHERE e.id = ?
                ORDER BY sub.name
                """;

        List<StudentSubjectMarkDTO> list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentSubjectMarkDTO dto = new StudentSubjectMarkDTO();
                    dto.setSubjectName(rs.getString("subject_name"));
                    dto.setFullMarks(rs.getDouble("full_marks"));
                    dto.setMarksObtained(rs.getObject("marks_obtained") != null ?
                            rs.getDouble("marks_obtained") : null);
                    list.add(dto);
                }
            }
        }
        return list;
    }

    public int getSubjectId(int examId, int teacherId) throws SQLException {
        String sql = """
                SELECT s.id AS subject_id
                FROM exams e
                JOIN semesters sem ON e.semester_id = sem.id
                JOIN subjects s ON s.semester_id = sem.id
                WHERE e.id = ?
                  AND s.assigned_teacher_id = ?
                  AND EXISTS (
                      SELECT 1 FROM subjects sub
                      WHERE sub.semester_id = sem.id
                        AND sub.assigned_teacher_id = ?
                  )
                LIMIT 1
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            ps.setInt(2, teacherId);
            ps.setInt(3, teacherId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("subject_id");
                }
            }
        }

        return -1; // Not found or not accessible to this teacher
    }

    public void saveMarks(int examId, int subjectId, int studentId, double marksObtained) throws SQLException {
        String sql = "INSERT INTO marks (exam_id, student_id, subject_id, marks_obtained, total_marks) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE marks_obtained = VALUES(marks_obtained)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            ps.setInt(2, studentId);
            ps.setInt(3, subjectId);
            ps.setDouble(4, marksObtained);
            ps.setDouble(5, 100.00); // Or use exam.getFullMarks() if passed
            ps.executeUpdate();
        }
    }

    public boolean publishExam(int examId) throws SQLException {
        String sql = "UPDATE exams SET is_published = TRUE WHERE id = ? AND is_published = FALSE";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
