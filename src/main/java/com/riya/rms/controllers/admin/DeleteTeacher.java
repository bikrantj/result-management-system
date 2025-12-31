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

@WebServlet("/admin/teachers/delete")
public class DeleteTeacher extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String teacherId = req.getParameter("teacherId");

        if (teacherId != null && !teacherId.isBlank()) {
            Connection con = DBConnection.getConnection();
            UserRepository userRepo = new UserRepository(con);

            userRepo.deleteById(teacherId);
        }

        // PRG pattern
        resp.sendRedirect(req.getContextPath() + "/admin/teachers/");
    }
}
