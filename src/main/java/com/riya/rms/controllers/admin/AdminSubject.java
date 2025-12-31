package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Subject;
import com.riya.rms.repositories.SubjectRepository;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/subjects/")
public class AdminSubject extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
//TODO: Fetch all subjects and set to request attribute
        Connection con = DBConnection.getConnection();
        SubjectRepository repo = new SubjectRepository(con);

        List<Subject> subjects = repo.findAll();
//TODO: Inject courses and teachers to request attribute
        req.setAttribute("subjects", subjects);

        Navigator.navigateTo(Pages.ADMIN_SUBJECTS, req, res);
    }


}
