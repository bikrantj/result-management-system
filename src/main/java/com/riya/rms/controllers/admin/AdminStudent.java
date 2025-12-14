package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Course;
import com.riya.rms.models.User;
import com.riya.rms.repositories.CourseRepository;
import com.riya.rms.repositories.EnrollmentRepository;
import com.riya.rms.repositories.UserRepository;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/students/")
public class AdminStudent extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws jakarta.servlet.ServletException, java.io.IOException {
        // Implementation for handling GET requests for admin students
        //        Get connection with database
        Connection con = DBConnection.getConnection();

//        Get course database repository
        UserRepository userRepo = new UserRepository(con);

//        Also get all courses
        CourseRepository courseRepository = new CourseRepository(con);

        List<Course> courses = courseRepository.findAll();

//        Get all the courses
        List<User> users = userRepo.findAll();

//        Only select the "STUDENT" user
        List<User> filtered = users.stream()
                .filter(user -> user.getRole() != null && user.getRole().toString().equals("STUDENT"))
                .toList();

        req.setAttribute("users", filtered);
        req.setAttribute("courses", courses);
        Navigator.navigateTo(Pages.ADMIN_STUDENT, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws jakarta.servlet.ServletException, java.io.IOException {
        // Implementation for handling UPDATE requests for admin students
        Connection con = DBConnection.getConnection();
        try {
            con.setAutoCommit(false);

            int userId = Integer.parseInt(req.getParameter("id"));
            System.out.println("Starting transaction to update user with ID: " + userId);

            String username = req.getParameter("username");
            String fullName = req.getParameter("fullName");

//            Print incoming parameters
            System.out.println("Updating user with ID: " + userId);
            System.out.println("New username: " + username);
            System.out.println("New full name: " + fullName);


            UserRepository userRepo = new UserRepository(con);

//            First update the user itself.
            User user = new User();
            user.setId(userId);
            user.setUsername(username);
            user.setName(fullName);
            userRepo.updateUser(user);

            int courseId = Integer.parseInt(req.getParameter("courseId"));
            int semesterId = Integer.parseInt(req.getParameter("semesterId"));

            EnrollmentRepository enrollmentRepo =
                    new EnrollmentRepository(con);

            enrollmentRepo.createOrUpdateEnrollment(userId, courseId, semesterId);


            con.commit();
            System.out.println("User updated successfully");
            resp.sendRedirect(req.getContextPath() + "/admin/students/");


        } catch (Exception e) {
            System.out.println("User update failed. Rolling back changes.");
            try {
                con.rollback();
            } catch (Exception ignored) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (Exception ignored) {
            }
        }
    }


}
