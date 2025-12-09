package com.riya.rms.repositories;

import com.riya.rms.models.Course;
import com.riya.rms.utils.RomanNumbers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
