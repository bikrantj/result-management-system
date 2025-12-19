package com.riya.rms.filters;

import com.riya.rms.db.DBConnection;
import com.riya.rms.models.User;
import com.riya.rms.repositories.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebFilter("/*")
public class PersistentLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("REMEMBER_ME".equals(c.getName())) {

                        int userId = Integer.parseInt(c.getValue());

                        Connection con = DBConnection.getConnection();
                        UserRepository repo = new UserRepository(con);

                        User user = repo.findById(userId);
                        if (user != null) {
                            HttpSession newSession = request.getSession();
                            newSession.setAttribute("user", user);
                        }
                        break;
                    }
                }
            }
        }

        chain.doFilter(req, res);
    }
}