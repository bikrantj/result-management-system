package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Subject;
import com.riya.rms.repositories.SubjectRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/teachers/subjects")
public class AdminTeacherSubjects extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int teacherId = Integer.parseInt(req.getParameter("teacherId"));

        Connection con = DBConnection.getConnection();
        SubjectRepository subjectRepo = new SubjectRepository(con);

        List<Subject> subjects = subjectRepo.findByTeacherId(teacherId);


        req.setAttribute("subjects", subjects);
    }
}
