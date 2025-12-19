package com.riya.rms.controllers;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.User;
import com.riya.rms.repositories.UserRepository;
import com.riya.rms.utils.NavigationType;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println("Login attempt for user: " + username);
        System.out.println("Login attempt for password: " + password);


        Connection con = DBConnection.getConnection();
        UserRepository userRepo = new UserRepository(con);

        User user = userRepo.findByUsername(username);

        if (user == null) {
            System.out.println("User not found: " + username);
            req.setAttribute("error", "User doesn't exist");
            Navigator.navigateTo(Pages.INDEX, req, resp, NavigationType.FORWARD);
            return;
        }

        // TODO: replace with password hash match later
        if (!user.getPassword().equals(password)) {
            System.out.println("DB PASS" + user.getPassword());
            System.out.println("Password Incorrect: " + password);
            req.setAttribute("error", "Invalid password");
            Navigator.navigateTo(Pages.INDEX, req, resp, NavigationType.FORWARD);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        Cookie remember = new Cookie("REMEMBER_ME", String.valueOf(user.getId()));
        remember.setMaxAge(7 * 24 * 60 * 60); // 7 days
        remember.setHttpOnly(true);
        remember.setPath(req.getContextPath());

        resp.addCookie(remember);

        switch (user.getRole()) {
            case ADMIN:
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                break;

            case TEACHER:
                resp.sendRedirect(req.getContextPath() + "/teacher/dashboard");
                break;

            case STUDENT:
                resp.sendRedirect(req.getContextPath() + "/student/dashboard");
                break;
        }
    }
}
