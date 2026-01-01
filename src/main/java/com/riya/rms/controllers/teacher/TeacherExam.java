package com.riya.rms.controllers.teacher;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Exam;
import com.riya.rms.models.StudentMarkDTO;
import com.riya.rms.models.StudentSubjectMarkDTO;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacher/exams/*")
public class TeacherExam extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Connection con = DBConnection.getConnection();
        ExamRepository examRepo = new ExamRepository(con);
        String pathInfo = req.getPathInfo(); // /{id}
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/teacher/dashboard");
            return;
        }

        User teacher = (User) req.getSession().getAttribute("user");
        int teacherId = teacher.getId();

        int examId;
        try {
            examId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/teacher/dashboard");
            return;
        }

        Exam exam = examRepo.findById(examId, teacherId); // create this method
        if (exam == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<StudentMarkDTO> students =
                null;
        try {
            students = examRepo.findStudentsForExam(examId, teacherId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int subjectId = 0;
        try {
            subjectId = examRepo.getSubjectId(examId, teacherId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("HELLO" + subjectId);

        StudentSubjectMarkDTO scheme =
                null;
        try {
            scheme = examRepo.findSubjectMarkingScheme(examId, subjectId);
            System.out.println("Scheme loaded: " + scheme.getFullMarks());
        } catch (SQLException e) {
            System.out.println("Scheme loaded failed: ");
            throw new RuntimeException(e);
        }

        System.out.println("Scheme loaded: " + scheme.getFullMarks());

        req.setAttribute("scheme", scheme);
        req.setAttribute("exam", exam);
        req.setAttribute("students", students);
        Navigator.navigateTo(Pages.TEACHER_EXAM_MARKS, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User teacher = (User) req.getSession().getAttribute("user");

        int examId = Integer.parseInt(req.getPathInfo().substring(1));

        double totalMarks = Double.parseDouble(req.getParameter("totalMarks"));
        double passMarks = Double.parseDouble(req.getParameter("passMarks"));

        if (passMarks > totalMarks) {
            req.setAttribute("error", "Pass marks cannot exceed full marks.");
            reload(req, resp, examId, teacher.getId());
            return;
        }

        Connection con = DBConnection.getConnection();
        ExamRepository repo = new ExamRepository(con);

        int subjectId;
        try {
            subjectId = repo.getSubjectId(examId, teacher.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<StudentMarkDTO> students;
        try {
            students = repo.findStudentsForExam(examId, teacher.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            con.setAutoCommit(false);

            for (StudentMarkDTO s : students) {
                String val = req.getParameter("marks_" + s.getStudentId());
                double marks = (val == null || val.isBlank()) ? 0 : Double.parseDouble(val);

                if (marks > totalMarks || marks < 0) {
                    throw new IllegalArgumentException(
                            "Invalid marks for " + s.getStudentName());
                }

                repo.saveMarks(
                        examId,
                        subjectId,
                        s.getStudentId(),
                        marks,
                        totalMarks,
                        passMarks
                );
            }

            con.commit();
            req.setAttribute("success", "Marks saved successfully.");

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ignored) {
            }
            req.setAttribute("error", e.getMessage());
        }

        reload(req, resp, examId, teacher.getId());
    }

    private void reload(HttpServletRequest req, HttpServletResponse resp,
                        int examId, int teacherId)
            throws ServletException, IOException {

        ExamRepository repo = new ExamRepository(DBConnection.getConnection());

        Exam exam = repo.findById(examId, teacherId);

        List<StudentMarkDTO> students;
        try {
            students = repo.findStudentsForExam(examId, teacherId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int subjectId;
        try {
            subjectId = repo.getSubjectId(examId, teacherId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        StudentSubjectMarkDTO scheme;
        try {
            scheme = repo.findSubjectMarkingScheme(examId, subjectId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("exam", exam);
        req.setAttribute("students", students);
        req.setAttribute("scheme", scheme); // ✅ REQUIRED

        req.getRequestDispatcher("/WEB-INF/teacher/exam-marks.jsp")
                .forward(req, resp);
    }

}