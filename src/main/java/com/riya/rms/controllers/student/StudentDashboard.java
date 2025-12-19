package com.riya.rms.controllers.student;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Exam;
import com.riya.rms.models.User;
import com.riya.rms.repositories.ExamRepository;
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

@WebServlet("/student/dashboard")
public class StudentDashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User student = (User) req.getSession().getAttribute("user");

        Connection con = DBConnection.getConnection();
        ExamRepository examRepo = new ExamRepository(con);

        try {
            List<Exam> exams = examRepo.findExamsForStudent(student.getId());
            req.setAttribute("exams", exams);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Unable to load exams. Please try again later.");
        } finally {
            // Connection handled by DBConnection pool or closed appropriately
        }

        // Keep your existing Navigator if preferred
        Navigator.navigateTo(Pages.STUDENT_DASHBOARD, req, resp);
        // Alternatively: req.getRequestDispatcher("/WEB-INF/student/dashboard.jsp").forward(req, resp);
    }
}