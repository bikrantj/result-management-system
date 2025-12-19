package com.riya.rms.controllers.admin;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.Course;
import com.riya.rms.models.Subject;
import com.riya.rms.models.User;
import com.riya.rms.repositories.CourseRepository;
import com.riya.rms.repositories.SubjectRepository;
import com.riya.rms.repositories.UserRepository;
import com.riya.rms.utils.Navigator;
import com.riya.rms.utils.Pages;
import com.riya.rms.utils.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/teachers/")
public class AdminTeacher extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("Controller Hit ");
        Connection con = DBConnection.getConnection();

        UserRepository userRepo = new UserRepository(con);
        CourseRepository courseRepo = new CourseRepository(con);
        SubjectRepository subjectRepo = new SubjectRepository(con);

        // 1️⃣ Load all teachers (base page always needs this)
        List<User> teachers = userRepo.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.TEACHER)
                .toList();
        List<Course> courses = courseRepo.findAll();

        req.setAttribute("teachers", teachers);
        req.setAttribute("courses", courses);

        // 2️⃣ Optional: load subjects for modal
        String showSubjects = req.getParameter("showSubjects");
        String teacherIdStr = req.getParameter("teacherId");

        if ("true".equals(showSubjects) && teacherIdStr != null) {
            System.out.println("Loading subjects for teacher ID: " + teacherIdStr);
            int teacherId = Integer.parseInt(teacherIdStr);

            List<Subject> subjects =
                    subjectRepo.findByTeacherId(teacherId);

            User selectedTeacher = teachers.stream()
                    .filter(t -> t.getId() == teacherId)
                    .findFirst()
                    .orElse(null);

            req.setAttribute("subjects", subjects);
            req.setAttribute("selectedTeacher", selectedTeacher);
            req.setAttribute("openSubjectModal", true);
        }

        Navigator.navigateTo(Pages.ADMIN_TEACHER, req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Connection con = DBConnection.getConnection();

        try {
            con.setAutoCommit(false);

            int teacherId = Integer.parseInt(req.getParameter("id"));
            String username = req.getParameter("username");
            String fullName = req.getParameter("fullName");

            String subjectParam = req.getParameter("subjectId");
            Integer subjectId = (subjectParam == null || subjectParam.isBlank())
                    ? null
                    : Integer.parseInt(subjectParam);

            UserRepository userRepo = new UserRepository(con);
            SubjectRepository subjectRepo = new SubjectRepository(con);

            // 1️⃣ Update teacher identity
            User user = new User();
            user.setId(teacherId);
            user.setUsername(username);
            user.setName(fullName);
            userRepo.updateUser(user);

            // 2️⃣ Assign teacher to NEW subject only
            if (subjectId != null) {
                subjectRepo.updateAssignedTeacher(subjectId, teacherId);
            }

            con.commit();
            resp.sendRedirect(req.getContextPath() + "/admin/teachers/");

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ignored) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (Exception ignored) {
            }
        }
    }


}
