package com.riya.rms.controllers.teacher;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Exam;
import com.riya.rms.models.StudentMarkDTO;
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

        System.out.println("Retruning students marks for exam ID: " + examId + ", count: " + students.size());
        for (StudentMarkDTO s : students) {
            System.out.println("Student: " + s.getStudentName() + ", Marks: " + s.getMarks());
        }

        req.setAttribute("exam", exam);
        req.setAttribute("students", students);
        Navigator.navigateTo(Pages.TEACHER_EXAM_MARKS, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User teacher = (User) req.getSession().getAttribute("user");


        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/teacher/dashboard");
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

        Exam exam = examRepo.findById(examId, teacher.getId());
        if (exam == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int subjectId = 0; // Implement this method
        try {
            subjectId = examRepo.getSubjectId(examId, teacher.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (subjectId == -1) { // Assuming -1 indicates not found
            req.setAttribute("error", "Subject not found for this exam.");
            Navigator.navigateTo(Pages.TEACHER_EXAM_MARKS, req, resp);
            return;
        }

        List<StudentMarkDTO> students = null;
        try {
            students = examRepo.findStudentsForExam(examId, teacher.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            con.setAutoCommit(false);

            boolean hasErrors = false;
            StringBuilder errorMsg = new StringBuilder();

            for (StudentMarkDTO student : students) {
                String paramName = "marks_" + student.getStudentId();
                String marksStr = req.getParameter(paramName);

                if (marksStr == null) {
                    continue; // Skip if no input field (unexpected)
                }

                marksStr = marksStr.trim();
                if (marksStr.isEmpty()) {
                    // Treat empty as absent marks (e.g., 0 or null); adjust as needed
                    examRepo.saveMarks(examId, subjectId, student.getStudentId(), 0.0);
                    continue;
                }

                double marks;
                try {
                    marks = Double.parseDouble(marksStr);
                } catch (NumberFormatException e) {
                    hasErrors = true;
                    errorMsg.append("Invalid marks for student ").append(student.getStudentName()).append(". ");
                    continue;
                }

                if (marks < 0 || marks > exam.getFullMarks()) {
                    hasErrors = true;
                    errorMsg.append("Marks out of range (0-").append(exam.getFullMarks())
                            .append(") for student ").append(student.getStudentName()).append(". ");
                    continue;
                }

                examRepo.saveMarks(examId, subjectId, student.getStudentId(), marks);
            }

            if (hasErrors) {
                con.rollback();
                req.setAttribute("error", errorMsg.toString());
            } else {
                con.commit();
                req.setAttribute("success", "Marks saved successfully.");
            }

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ignored) {
            }
            req.setAttribute("error", "An error occurred while saving marks: " + e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (Exception ignored) {
            }
        }

        try {
            loadExamDataAndForward(req, resp, examId, teacher.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to avoid code duplication
    private void loadExamDataAndForward(HttpServletRequest req, HttpServletResponse resp,
                                        int examId, int teacherId)
            throws ServletException, IOException, SQLException {

        Connection con = DBConnection.getConnection();
        ExamRepository examRepo = new ExamRepository(con);

        Exam exam = examRepo.findById(examId, teacherId);
        if (exam == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<StudentMarkDTO> students = examRepo.findStudentsForExam(examId, teacherId);

        req.setAttribute("exam", exam);
        req.setAttribute("students", students);

        req.getRequestDispatcher("/WEB-INF/teacher/exam-marks.jsp").forward(req, resp);
    }
}