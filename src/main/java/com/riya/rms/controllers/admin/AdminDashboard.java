package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.repositories.CourseRepository;
import com.riya.rms.repositories.SubjectRepository;
import com.riya.rms.repositories.UserRepository;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/dashboard")
public class AdminDashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Connection con = DBConnection.getConnection();

        CourseRepository courseRepo = new CourseRepository(con);
        UserRepository userRepo = new UserRepository((con));
        SubjectRepository subjectRepo = new SubjectRepository((con));

        int totalCourses = courseRepo.countCourses();
        int totalStudents = userRepo.countStudents();
        int totalTeachers = userRepo.countTeachers();
        int totalSubjects = subjectRepo.countSubjects();

        req.setAttribute("totalCourses", totalCourses);
        req.setAttribute("totalStudents", totalStudents);
        req.setAttribute("totalTeachers", totalTeachers);
        req.setAttribute("totalSubjects", totalSubjects);

        Navigator.navigateTo(Pages.ADMIN_DASHBOARD, req, resp);
    }
}
