package com.riya.rms.controllers.student;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Exam;
import com.riya.rms.models.StudentSubjectMarkDTO;
import com.riya.rms.models.User;
import com.riya.rms.repositories.ExamRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/student/exams/*")
public class StudentExam extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User student = (User) req.getSession().getAttribute("user");
    

        String pathInfo = req.getPathInfo(); // e.g., /123
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/student/dashboard");
            return;
        }

        int examId;
        try {
            examId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Connection con = DBConnection.getConnection();
        ExamRepository examRepo = new ExamRepository(con);

        try {
            Exam exam = examRepo.findByIdForStudent(examId, student.getId()); // New secure method recommended
            if (exam == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Exam not found or not accessible.");
                return;
            }

            List<StudentSubjectMarkDTO> marks = examRepo.findMarksForExamAndStudent(examId, student.getId());

            req.setAttribute("exam", exam);
            req.setAttribute("marks", marks);

            req.getRequestDispatcher("/WEB-INF/student/exam-detail.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Unable to load results. Please try again later.");
            req.getRequestDispatcher("/WEB-INF/student/exam-detail.jsp").forward(req, resp);
        }
    }
}