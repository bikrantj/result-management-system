package com.riya.rms.filters;

import com.riya.rms.models.User;
import com.riya.rms.utils.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/admin/*",
        "/teacher/*",
        "/student/*"
})
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        String path = req.getRequestURI();

        if (path.startsWith(req.getContextPath() + "/admin")
                && user.getRole() != Role.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/unauthorized.jsp");
            return;
        }

        if (path.startsWith(req.getContextPath() + "/teacher")
                && user.getRole() != Role.TEACHER) {
            resp.sendRedirect(req.getContextPath() + "/unauthorized.jsp");
            return;
        }

        if (path.startsWith(req.getContextPath() + "/student")
                && user.getRole() != Role.STUDENT) {
            resp.sendRedirect(req.getContextPath() + "/unauthorized.jsp");
            return;
        }

        chain.doFilter(request, response);
    }
}
