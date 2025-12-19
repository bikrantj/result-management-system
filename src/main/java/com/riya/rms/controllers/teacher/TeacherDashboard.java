package com.riya.rms.controllers.teacher;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Exam;
import com.riya.rms.models.User;
import com.riya.rms.repositories.ExamRepository;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/teacher/dashboard")
public class TeacherDashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        User teacher = (User) req.getSession().getAttribute("user");

        Connection con = DBConnection.getConnection();
        ExamRepository examRepo = new ExamRepository(con);
        List<Exam> exams = examRepo.findAllForTeacher(teacher.getId());

        req.setAttribute("exams", exams);
        Navigator.navigateTo(Pages.TEACHER_DASHBOARD, req, resp);
    }
}