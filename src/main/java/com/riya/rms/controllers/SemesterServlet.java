package com.riya.rms.controllers;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Semester;
import com.riya.rms.repositories.SemesterRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/semesters")
public class SemesterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String courseIdParam = req.getParameter("courseId");
        resp.setContentType("application/json");

        if (courseIdParam == null) {
            resp.getWriter().write("[]");
            return;
        }

        int courseId = Integer.parseInt(courseIdParam);

        try (Connection con = DBConnection.getConnection()) {

            SemesterRepository repo = new SemesterRepository(con);
            List<Semester> semesters = repo.findByCourseId(courseId);

//            String json = new Gson().toJson(semesters);
//            resp.getWriter().write(json);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("[]");
        }
    }
}
