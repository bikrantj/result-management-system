package com.riya.rms.repositories;

import com.riya.rms.models.Course;
import com.riya.rms.models.Semester;
import com.riya.rms.utils.RomanNumbers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CourseRepository {
    private Connection con;

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

    //    TODO: Join courses with student to get
    public List<Course> findAll() {

        String sql = """
                    SELECT 
                        c.id           AS course_id,
                        c.name         AS course_name,
                        c.code         AS course_code,
                        c.semester_count,
                        COUNT(se.student_id) AS total_students,
                
                        s.id           AS semester_id,
                        s.name         AS semester_name
                
                    FROM courses c
                    LEFT JOIN semesters s 
                        ON s.course_id = c.id
                    LEFT JOIN student_enrollments se 
                        ON se.course_id = c.id
                
                    GROUP BY 
                        c.id, c.name, c.code, c.semester_count,
                        s.id, s.name
                
                    ORDER BY c.id, s.id
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
                if (!rs.wasNull()) {
                    Semester semester = new Semester();
                    semester.setId(semesterId);
                    semester.setName(rs.getString("semester_name"));
                    semester.setCourseId(courseId);

                    course.addSemester(semester);
                }
            }

            return new ArrayList<>(courseMap.values());

        } catch (Exception e) {
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
