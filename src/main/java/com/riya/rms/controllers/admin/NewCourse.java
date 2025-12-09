package com.riya.rms.controllers.admin;

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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/courses/new-course")
public class NewCourse extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Navigator.navigateTo(Pages.ADMIN_NEW_COURSE, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

//        Get course details from request
        String courseName = req.getParameter("courseName");
        String courseCode = req.getParameter("courseCode");
        int semesters = Integer.parseInt(req.getParameter("semesterCount"));

//        Get connection with database
        Connection con = DBConnection.getConnection();
//        Get course database repository
        CourseRepository courseRepo = new CourseRepository(con);

//        Check if course already exists
        Course course = courseRepo.findByCode(courseCode);

        // Get the current session(connection)
        HttpSession session = req.getSession();

        if (course != null) {
            System.out.println("Course exists");
            System.out.println(course.getName());
            System.out.println(course.getCode());
            System.out.println(course.getSemesterCount());
//            If course exists, return with error
            session.setAttribute("error", "Course with this code already exists");

            Navigator.navigateTo(Pages.ADMIN_NEW_COURSE, req, resp);
            return;
        }

//        If course is new, create new course object

        course = new Course();
        course.setName(courseName);
        course.setCode(courseCode);
        course.setSemesterCount(semesters);

//        Insert new course to database
        boolean success = courseRepo.createCourse(course);


//        Not success, return and show error
        if (!success) {
            System.out.println("Error creating course");
            session.setAttribute("error", "Server error while creating course.");
            Navigator.navigateTo(Pages.ADMIN_NEW_COURSE, req, resp);
            return;
        }

//        Success, return with success message
        System.out.println("Success creating course");
        session.setAttribute("success", "Course created successfully.");
        req.setAttribute("showModal", true);   // If you want modal to stay open
        Navigator.navigateTo(Pages.ADMIN_NEW_COURSE, req, resp);
    }
}
