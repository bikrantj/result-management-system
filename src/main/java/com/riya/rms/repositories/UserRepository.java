package com.riya.rms.repositories;

import com.riya.rms.models.Course;
import com.riya.rms.models.Semester;
import com.riya.rms.models.User;
import com.riya.rms.utils.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Connection con;

    public UserRepository(Connection con) {
        this.con = con;
    }

    public boolean createUser(User user) {
        try {
            String sql = "INSERT INTO users (username, password, name, role) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getRole().toStringValue());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            System.out.println("Error creating user:" + e);
            e.printStackTrace();
        }
        return false;
    }

    public User findByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setName(rs.getString("name"));
                u.setRole(Role.toEnum(rs.getString("role")));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findById(int id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setName(rs.getString("name"));
                u.setRole(Role.toEnum(rs.getString("role")));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> findAll() {
        // Implementation for fetching all users can be added here
        try {
            String sql = """
                    SELECT\s
                        u.id,
                        u.username,
                        u.name,
                        u.role,
                        c.name  AS course_name,
                        c.id AS course_id,
                        c.code AS course_code,
                        s.id AS semester_id,
                        s.name  AS semester_name,
                        s.course_id AS semester_course_id
                    FROM users u
                    LEFT JOIN student_enrollments se ON se.student_id = u.id
                    LEFT JOIN courses c ON c.id = se.course_id
                    LEFT JOIN semesters s ON s.id = se.current_semester_id
                    """;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();

            while (rs.next()) {
                User u = new User();

                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setName(rs.getString("name"));
                u.setRole(Role.toEnum(rs.getString("role"))); // or EnumUtil

                // Enrollment info (nullable for non-students)
                Course c = new Course();
                c.setId(rs.getInt("course_id"));
                c.setName(rs.getString("course_name"));
                c.setCode(rs.getString("course_code"));
                u.setCourse(c);

                Semester s = new Semester();
                s.setId(rs.getInt("semester_id"));
                s.setName(rs.getString("semester_name"));
                s.setCourseId(rs.getInt("semester_course_id"));
                u.setSemester(s);
                users.add(u);
            }
            return users;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("Error fetching findAll users: " + e);
            return null;
        }
    }


}
