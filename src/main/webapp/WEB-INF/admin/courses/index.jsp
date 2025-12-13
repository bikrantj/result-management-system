<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.riya.rms.models.Course" %>
<%@ page import="java.util.List" %>
<%
    // Extract data from request attributes (set by servlet)
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    int totalStudents = (int) request.getAttribute("totalStudents");

    // Set active menu for sidebar
    request.setAttribute("activeMenu", "courses");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Courses - Admin Panel</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>
    
</head>

<body class="bg-gray-50 font-sans antialiased">

<div class="flex min-h-screen">

    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/shared/sidebar-admin.jsp"/>

    <!-- Main Content -->
    <main class="flex-1 p-6">

        <!-- Page Header -->
        <div class="mb-8">
            <div class="flex items-center justify-between">
                <div>
                    <h1 class="text-3xl font-bold text-gray-900 mb-2">Course Management</h1>
                    <p class="text-gray-600">Manage all courses and their details in one place</p>
                </div>

                <!-- Add New Course Button -->
                <a href="${pageContext.request.contextPath}/admin/courses/new-course"
                   class="group relative inline-flex items-center justify-center overflow-hidden rounded-xl
                          bg-gradient-to-r from-blue-600 to-indigo-600 px-6 py-3 text-white font-semibold
                          shadow-lg transition-all duration-300 hover:shadow-xl hover:-translate-y-0.5
                          hover:from-blue-700 hover:to-indigo-700 active:scale-95">

                    <svg class="w-5 h-5 mr-2 group-hover:rotate-90 transition-transform duration-300"
                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M12 4v16m8-8H4"></path>
                    </svg>
                    Add New Course
                </a>
            </div>

            <!-- Stats Cards -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mt-6">
                <div class="bg-gradient-to-r from-blue-50 to-indigo-50 rounded-2xl p-5 border border-blue-100">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-sm font-medium text-gray-600">Total Courses</p>
                            <p class="text-3xl font-bold text-gray-900 mt-2"><%= courses.size() %>
                            </p>
                        </div>
                        <div class="h-12 w-12 rounded-xl bg-gradient-to-br from-blue-500 to-indigo-500
                                    flex items-center justify-center shadow-md">

                        </div>
                    </div>

                </div>

                <div class="bg-gradient-to-r from-emerald-50 to-teal-50 rounded-2xl p-5 border border-emerald-100">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-sm font-medium text-gray-600">Active Students</p>
                            <p class="text-3xl font-bold text-gray-900 mt-2"><%= totalStudents %>
                            </p>
                        </div>
                        <div class="h-12 w-12 rounded-xl bg-gradient-to-br from-emerald-500 to-teal-500
                                    flex items-center justify-center shadow-md">

                        </div>
                    </div>

                </div>

            </div>
        </div>

        <!-- Courses Table Section -->
        <div class="bg-white rounded-2xl shadow-lg border border-gray-100 p-1">
            <div class="p-5 border-b border-gray-100">
                <div class="flex items-center justify-between">
                    <div>
                        <h2 class="text-lg font-semibold text-gray-900">All Courses</h2>
                        <p class="text-sm text-gray-600 mt-1">List of all available courses with details</p>
                    </div>
                </div>
            </div>

            <!-- Table -->
            <jsp:include page="courses_table.jsp"/>


        </div>

    </main>

</div>

</body>
</html>