package com.riya.rms.repositories;

import com.riya.rms.models.Course;
import com.riya.rms.utils.RomanNumbers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        try {
            String sql = "SELECT c.*, COUNT(se.student_id) as total_students FROM courses c LEFT JOIN student_enrollments se ON c.id = se.course_id GROUP BY c.id, c.name, c.code, c.semester_count ORDER BY c.id";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Course> courses = new ArrayList<>();

            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setCode(rs.getString("code"));
                c.setSemesterCount(rs.getInt("semester_count"));
                c.setTotalStudents(rs.getInt("total_students"));
                courses.add(c);
            }
            return courses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
