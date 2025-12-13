package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Course;
import com.riya.rms.models.User;
import com.riya.rms.repositories.CourseRepository;
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


}
