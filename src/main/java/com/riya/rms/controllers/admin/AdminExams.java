package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Exam;
import com.riya.rms.repositories.CourseRepository;
import com.riya.rms.repositories.ExamRepository;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/exams/")
public class AdminExams extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        System.out.println("Admin Exams accessed");
        Connection con = DBConnection.getConnection();

        CourseRepository courseRepo = new CourseRepository(con);
        ExamRepository examRepo = new ExamRepository(con);

        req.setAttribute("courses", courseRepo.findAll());
        req.setAttribute("exams", examRepo.findAll());

        Navigator.navigateTo(Pages.ADMIN_EXAMS, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Connection con = DBConnection.getConnection();
        ExamRepository examRepo = new ExamRepository(con);

        Exam exam = new Exam();
        exam.setName(req.getParameter("name"));
        exam.setCourseId(Integer.parseInt(req.getParameter("courseId")));
        exam.setSemesterId(Integer.parseInt(req.getParameter("semesterId")));

        try {
            examRepo.create(exam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/exams/");
    }
}