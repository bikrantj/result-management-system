package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/students/delete")
public class DeleteStudent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String studentId = req.getParameter("studentId");

        if (studentId != null && !studentId.isBlank()) {
            Connection con = DBConnection.getConnection();
            UserRepository userRepo = new UserRepository(con);

            userRepo.deleteById(studentId);
        }

        // PRG pattern
        resp.sendRedirect(req.getContextPath() + "/admin/students/");
    }
}
