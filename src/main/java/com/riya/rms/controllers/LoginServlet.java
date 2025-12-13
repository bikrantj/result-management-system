package com.riya.rms.controllers;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.User;
import com.riya.rms.repositories.UserRepository;
import com.riya.rms.utils.NavigationType;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Connection con = DBConnection.getConnection();
        UserRepository userRepo = new UserRepository(con);

        User user = userRepo.findByUsername(username);

        if (user == null) {
            req.setAttribute("error", "Invalid username or password");
            Navigator.navigateTo(Pages.INDEX, req, resp, NavigationType.FORWARD);
            return;
        }

        // TODO: replace with password hash match later
        if (!user.getPassword().equals(password)) {
            req.setAttribute("error", "Invalid username or password");
            Navigator.navigateTo(Pages.INDEX, req, resp, NavigationType.FORWARD);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        switch (user.getRole()) {
            case ADMIN:
                System.out.println("Navigating to admin dashboard");
                Navigator.navigateTo(Pages.ADMIN_DASHBOARD, req, resp);
                break;

            case TEACHER:
                Navigator.navigateTo(Pages.TEACHER_DASHBOARD, req, resp);
                break;

            case STUDENT:
                Navigator.navigateTo(Pages.STUDENT_DASHBOARD, req, resp);
                break;
        }
    }
}
