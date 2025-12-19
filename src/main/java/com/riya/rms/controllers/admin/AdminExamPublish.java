package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.User;
import com.riya.rms.repositories.ExamRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/admin/exam/publish/*")
public class AdminExamPublish extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Security: Ensure only admin can access
        User user = (User) req.getSession().getAttribute("user");

        String pathInfo = req.getPathInfo(); // e.g., /123
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Exam ID is required.");
            return;
        }

        int examId;
        try {
            examId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Exam ID.");
            return;
        }

        Connection con = DBConnection.getConnection();
        ExamRepository examRepo = new ExamRepository(con);

        try {
            boolean updated = examRepo.publishExam(examId);

            if (updated) {
                req.getSession().setAttribute("success", "Exam results published successfully.");
            } else {
                req.getSession().setAttribute("error", "Exam not found or already published.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "Database error while publishing exam.");
        }

        // Redirect back to the exam list page (PRG pattern)
        // Adjust the redirect URL to match your actual admin exam list page
        resp.sendRedirect(req.getContextPath() + "/admin/exams/");
    }
}