package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.repositories.SubjectRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/subjects/delete")
public class DeleteSubject extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String subjectId = req.getParameter("subjectId");

        if (subjectId != null && !subjectId.isBlank()) {
            Connection con = DBConnection.getConnection();
            SubjectRepository subjectRepo = new SubjectRepository(con);

            subjectRepo.deleteById(subjectId);
        }

        // PRG pattern: redirect after delete
        resp.sendRedirect(req.getContextPath() + "/admin/subjects/");
    }
}
