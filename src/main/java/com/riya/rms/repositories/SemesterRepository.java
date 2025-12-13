package com.riya.rms.repositories;


import com.riya.rms.models.Semester;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SemesterRepository {

    private Connection con;

    public SemesterRepository(Connection con) {
        this.con = con;
    }

    public List<Semester> findByCourseId(int courseId) throws SQLException {

        String sql = "SELECT id, name FROM semesters WHERE course_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, courseId);

        ResultSet rs = ps.executeQuery();
        List<Semester> semesters = new ArrayList<>();

        while (rs.next()) {
            Semester s = new Semester();
            s.setId(rs.getInt("id"));
            s.setName(rs.getString("name"));
            semesters.add(s);
        }

        return semesters;
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
