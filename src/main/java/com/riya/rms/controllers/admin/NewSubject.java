package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Course;
import com.riya.rms.models.Subject;
import com.riya.rms.models.User;
import com.riya.rms.repositories.CourseRepository;
import com.riya.rms.repositories.SubjectRepository;
import com.riya.rms.repositories.UserRepository;
import com.riya.rms.services.UserService;
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
import java.util.List;

@WebServlet("/admin/subjects/new-subject")
public class NewSubject extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //        Get course database repository
        Connection con = DBConnection.getConnection();
        CourseRepository courseRepo = new CourseRepository(con);
        UserRepository userRepo = new UserRepository(con);
        UserService userService = new UserService(userRepo);
//        Get all the courses
        List<Course> courses = courseRepo.findAll();

        List<User> users = userService.findAll(Role.TEACHER);
        req.setAttribute("courses", courses);
        req.setAttribute("teachers", users);
        Navigator.navigateTo(Pages.ADMIN_NEW_SUBJECT, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
//        Get course details from request
        String subjectName = req.getParameter("subjectName");
        String subjectCode = req.getParameter("subjectCode");
        int semesterId = Integer.parseInt(req.getParameter("semesterId"));

        String teacherIdParam = req.getParameter("teacherId");

        Integer teacherId = null; // nullable on purpose

        if (teacherIdParam != null && !teacherIdParam.isBlank()) {
            teacherId = Integer.parseInt(teacherIdParam);
        }

        try (Connection connection = DBConnection.getConnection()) {

            SubjectRepository subjectRepository = new SubjectRepository(connection);

            if (subjectRepository.findByCode(subjectCode) != null) {
                session.setAttribute("error", "Subject code already exists.");
                resp.sendRedirect(req.getContextPath() + "/admin/subjects/new-subject");
                return;
            }

            Subject subject = new Subject();
            subject.setName(subjectName);
            subject.setCode(subjectCode);
            subject.setSemesterId(semesterId);
            subject.setAssignedTeacherId(teacherId);

            subjectRepository.create(subject);

            session.setAttribute("success", "Subject created successfully.");
            resp.sendRedirect(req.getContextPath() + "/admin/subjects/new-subject");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Failed to create subject.");
            resp.sendRedirect(req.getContextPath() + "/admin/subjects/new-subject");
        }

    }
}
