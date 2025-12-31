package com.riya.rms.repositories;

import com.riya.rms.models.Course;
import com.riya.rms.models.Semester;
import com.riya.rms.models.Subject;
import com.riya.rms.utils.RomanNumbers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CourseRepository {
    private final Connection con;

    public CourseRepository(Connection con) {
        this.con = con;
    }

    public Course findByCode(String code) {
        try {
            String sql = "SELECT * FROM courses WHERE code = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Course c = new Course();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setCode(rs.getString("code"));
                c.setSemesterCount(rs.getInt("semester_count"));
                return c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countCourses() {
        String sql = "SELECT COUNT(*) FROM courses";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteById(int courseId) {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    TODO: Join courses with student to get
    public List<Course> findAll() {

        String sql = """
                    SELECT
                        c.id   AS course_id,
                        c.name AS course_name,
                        c.code AS course_code,
                        c.semester_count,
                
                        COALESCE(sc.total_students, 0) AS total_students,
                
                        s.id   AS semester_id,
                        s.name AS semester_name,
                
                        sub.id   AS subject_id,
                        sub.name AS subject_name,
                        sub.code AS subject_code
                
                    FROM courses c
                
                    LEFT JOIN (
                        SELECT
                            e.course_id,
                            COUNT(DISTINCT e.student_id) AS total_students
                        FROM student_enrollments e
                        GROUP BY e.course_id
                    ) sc ON sc.course_id = c.id
                
                    LEFT JOIN semesters s ON s.course_id = c.id
                    LEFT JOIN subjects sub ON sub.semester_id = s.id
                
                    ORDER BY c.id, s.id, sub.id
                """;

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            Map<Integer, Course> courseMap = new LinkedHashMap<>();

            while (rs.next()) {

                int courseId = rs.getInt("course_id");

                Course course = courseMap.get(courseId);
                if (course == null) {
                    course = new Course();
                    course.setId(courseId);
                    course.setName(rs.getString("course_name"));
                    course.setCode(rs.getString("course_code"));
                    course.setSemesterCount(rs.getInt("semester_count"));
                    course.setTotalStudents(rs.getInt("total_students"));

                    courseMap.put(courseId, course);
                }

                int semesterId = rs.getInt("semester_id");
                Semester semester = null;

                if (!rs.wasNull()) {

                    // 🔹 Find existing semester
                    for (Semester s : course.getSemesters()) {
                        if (s.getId() == semesterId) {
                            semester = s;
                            break;
                        }
                    }

                    // 🔹 Create if not exists
                    if (semester == null) {
                        semester = new Semester();
                        semester.setId(semesterId);
                        semester.setName(rs.getString("semester_name"));
                        semester.setCourseId(courseId);
                        course.addSemester(semester);
                    }
                }

                int subjectId = rs.getInt("subject_id");
                if (semester != null && !rs.wasNull()) {

                    Subject subject = new Subject();
                    subject.setId(subjectId);
                    subject.setName(rs.getString("subject_name"));
                    subject.setCode(rs.getString("subject_code"));
                    subject.setSemesterId(semesterId);

                    semester.getSubjects().add(subject);
                }
            }

            return new ArrayList<>(courseMap.values());

        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }


    public boolean createCourse(Course course) {

        try {
//            Transaction
            con.setAutoCommit(false);
            String sql = "INSERT INTO courses (name, code, semester_count) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getName());
            ps.setString(2, course.getCode());
            ps.setInt(3, course.getSemesterCount());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int courseId = 0;

            if (rs.next()) {
                courseId = rs.getInt(1);
            }

            SemesterRepository semesterRepository = new SemesterRepository(con);
            for (int i = 1; i <= course.getSemesterCount(); i++) {
                String roman = RomanNumbers.toRoman(i);
                semesterRepository.createSemester(courseId, roman);
            }
            con.commit(); // Commit the transaction
            con.setAutoCommit(true);
            return true;
        } catch (Exception e) {
            try {
                con.rollback(); // Rollback transaction to previous safe-state
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;

    }
}
