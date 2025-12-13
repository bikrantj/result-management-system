package com.riya.rms.controllers.admin;

import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/students/")
public class AdminStudent  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws jakarta.servlet.ServletException, java.io.IOException {
        // Implementation for handling GET requests for admin students
        Navigator.navigateTo(Pages.ADMIN_STUDENT, req, resp);
    }


}
