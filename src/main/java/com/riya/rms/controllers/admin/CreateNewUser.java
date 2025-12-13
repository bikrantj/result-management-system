package com.riya.rms.controllers.admin;


import com.riya.rms.db.DBConnection;
import com.riya.rms.models.User;
import com.riya.rms.repositories.UserRepository;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import com.riya.rms.utils.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/create-new-user")
public class CreateNewUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Navigator.navigateTo(Pages.ADMIN_CREATE_NEW_USER, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

//        Get student details from request
        String role = req.getParameter("role");
//        Convert role to appropriate Role enum:
        Role userRole = Role.toEnum(role);

        String fullName = req.getParameter("fullName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

//        Get connection with database
        Connection con = DBConnection.getConnection();
//        Get student database repository
        UserRepository userRepo = new UserRepository(con);


        HttpSession session = req.getSession();

//        Check if user already exists
        User user = userRepo.findByUsername(username);

        if (user != null) {
//            Set error message and return
            System.out.println("Error creating new user");
            session.setAttribute("error", "User with this username already exists.");
            Navigator.navigateTo(Pages.ADMIN_CREATE_NEW_USER, req, resp);
            return;
        }
// If user doesn't exist, create new user
        user = new User();
        user.setName(fullName);
        user.setUsername(username);
//        TODO: Hash password before saving
        user.setPassword(password);
        user.setRole(userRole);

//        Insert new user to database
        boolean success = userRepo.createUser(user);


//        Not success, return and show error
        if (!success) {
            System.out.println("Error creating user");
            session.setAttribute("error", "Server error while creating user.");
            Navigator.navigateTo(Pages.ADMIN_CREATE_NEW_USER, req, resp);
            return;
        }

//        Success, return with success message
        System.out.println("Success creating user");
        session.setAttribute("success", "User created successfully.");
        Navigator.navigateTo(Pages.ADMIN_CREATE_NEW_USER, req, resp);
    }

}
