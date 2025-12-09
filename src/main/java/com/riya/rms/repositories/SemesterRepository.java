package com.riya.rms.repositories;


import java.sql.Connection;
import java.sql.PreparedStatement;

public class SemesterRepository {

    private Connection con;

    public SemesterRepository(Connection con) {
        this.con = con;
    }

    public void createSemester(int courseId, String semesterName) {
        try {
            String sql = "INSERT INTO semesters (name, course_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, semesterName);
            ps.setInt(2, courseId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
