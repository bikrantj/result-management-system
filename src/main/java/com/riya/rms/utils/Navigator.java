package com.riya.rms.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class Navigator {

    public static void navigateTo(Pages page,
                                  HttpServletRequest req,
                                  HttpServletResponse resp,
                                  NavigationType type,
                                  Map<String, Object> queryParams) throws IOException {

        String queryString = new QueryParamBuilder().build(queryParams);
        internal_navigateTo(page.getPath() + queryString, req, resp, type);

    }

    public static void navigateTo(Pages page,
                                  HttpServletRequest req,
                                  HttpServletResponse resp,
                                  Map<String, Object> queryParams) throws IOException {

        String queryString = new QueryParamBuilder().build(queryParams);
        internal_navigateTo(page.getPath() + queryString, req, resp, NavigationType.REDIRECT);
    }

    public static void navigateTo(Pages page,
                                  HttpServletRequest req,
                                  HttpServletResponse resp, NavigationType type) throws IOException {

        internal_navigateTo(page.getPath(), req, resp, type);
    }

    public static void navigateTo(Pages page,
                                  HttpServletRequest req,
                                  HttpServletResponse resp) throws IOException {

        internal_navigateTo(page.getPath(), req, resp, NavigationType.REDIRECT);
    }

    // Will receive the final path with query params appended
    private static void internal_navigateTo(String finalPath,
                                            HttpServletRequest req,
                                            HttpServletResponse resp,
                                            NavigationType type) throws IOException {

        try {
            switch (type) {
                case FORWARD -> req.getRequestDispatcher(finalPath).forward(req, resp);

                case REDIRECT -> resp.sendRedirect(req.getContextPath() + finalPath);
            }
        } catch (Exception e) {
            throw new IOException("Navigation failed: " + finalPath, e);
        }
    }
}
