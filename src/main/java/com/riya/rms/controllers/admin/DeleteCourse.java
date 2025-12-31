package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.repositories.CourseRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/courses/delete")
public class DeleteCourse extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String courseId = req.getParameter("courseId");

        if (courseId != null && !courseId.isBlank()) {

            Connection con = DBConnection.getConnection();
            CourseRepository courseRepo = new CourseRepository(con);

            courseRepo.deleteById(Integer.parseInt(courseId));
        }

        // Redirect back to course list after deletion
        resp.sendRedirect(req.getContextPath() + "/admin/courses/");

    }
}
