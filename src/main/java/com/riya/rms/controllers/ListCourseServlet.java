package com.riya.rms.controllers;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Course;
import com.riya.rms.repositories.CourseRepository;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/courses/")
public class ListCourseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

//        Get connection with database
        Connection con = DBConnection.getConnection();

//        Get course database repository
        CourseRepository courseRepo = new CourseRepository(con);

//        Get all the courses
        List<Course> courses = courseRepo.findAll();
        req.setAttribute("courses", courses);

        Navigator.navigateTo(Pages.ADMIN_COURSE, req, resp);
    }
}
