<%@ page import="com.riya.rms.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.Course" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Set active menu for sidebar

    // Extract all message variables at the top
    String errorMessage = (String) session.getAttribute("error");
    String successMessage = (String) session.getAttribute("success");

    // Clear session attributes after extracting
    if (errorMessage != null) session.removeAttribute("error");
    if (successMessage != null) session.removeAttribute("success");


    List<User> teachers = (List<User>) request.getAttribute("teachers");
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    request.setAttribute("activeMenu", "subjects");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Add New Subject - Admin Panel</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>
    <script>
        const COURSE_SEMESTERS = {
            <% for (Course c : courses) { %>
            "<%= c.getId() %>": [
                <% if (c.getSemesters() != null) {
                    for (var s : c.getSemesters()) { %>
                {
                    id: "<%= s.getId() %>",
                    name: "<%= s.getName() %>"
                },
                <% }} %>
            ],
            <% } %>
        };
    </script>
    <script src="${pageContext.request.contextPath}/js/student-edit.js"></script>
</head>

<body class="bg-gray-50">

<div class="flex min-h-screen">

    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/shared/sidebar-admin.jsp"/>

    <!-- Main Content -->
    <main class="flex-1 p-6">

        <!-- Simple Header with Back Button -->
        <div class="mb-8">
            <div class="flex items-center gap-4">
                <button onclick="window.history.back()"
                        class="px-4 py-2 rounded-lg border border-gray-300 bg-white text-gray-700
                               font-medium hover:bg-gray-50 transition-colors">
                    ← Back
                </button>
                <div>
                    <h1 class="text-2xl font-bold text-gray-900">Add New Subject</h1>
                    <p class="text-gray-600 mt-1">Create a new academic subject</p>
                </div>
            </div>
        </div>

        <!-- Form Container -->
        <div class="flex justify-center">
            <div class="w-full max-w-2xl bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden">

                <!-- Header -->
                <div class="bg-gradient-to-r from-blue-600 to-indigo-600 p-6">
                    <div class="flex items-center justify-between">
                        <div>
                            <h2 class="text-2xl font-bold text-white">Add New Subject</h2>
                            <p class="text-blue-100 text-sm mt-1">Create a new academic subject</p>
                        </div>
                        <div class="h-12 w-12 rounded-xl bg-white/10 backdrop-blur-sm flex items-center justify-center">
                            <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
                            </svg>
                        </div>
                    </div>
                </div>

                <!-- Content -->
                <div class="p-6">
                    <!-- Error Message -->
                    <% if (errorMessage != null) { %>
                    <div class="mb-6 p-4 rounded-xl bg-gradient-to-r from-red-50 to-red-100 border border-red-200
                text-red-700 font-medium shadow-sm">
                        <div class="flex items-center">
                            <%= errorMessage %>
                        </div>
                    </div>
                    <% } %>

                    <!-- Success Message -->
                    <% if (successMessage != null) { %>
                    <div class="mb-6 p-4 rounded-xl bg-gradient-to-r from-emerald-50 to-green-100 border border-emerald-200
                text-emerald-700 font-medium shadow-sm">
                        <div class="flex items-center">
                            <%= successMessage %>
                        </div>
                    </div>
                    <% } %>
                    <form action="${pageContext.request.contextPath}/admin/subjects/new-subject" method="post"
                          class="space-y-6">

                        <!-- Subject Name Field -->
                        <div>
                            <label for="subjectName" class="block text-sm font-medium text-gray-700 mb-2">
                                Subject Name
                            </label>
                            <div class="relative">
                                <input type="text" name="subjectName" id="subjectName"
                                       class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                              focus:outline-none focus:ring-2 focus:ring-blue-500
                                              focus:border-transparent transition-all duration-200
                                              bg-white placeholder-gray-400"
                                       placeholder="e.g., Introduction to Programming"
                                       required>
                            </div>
                            <p class="text-xs text-gray-500 mt-2 ml-1">Enter the full name of the subject</p>
                        </div>

                        <!-- Subject Code Field -->
                        <div>
                            <label for="subjectCode" class="block text-sm font-medium text-gray-700 mb-2">
                                Subject Code
                            </label>
                            <div class="relative">
                                <input type="text" name="subjectCode" id="subjectCode"
                                       class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                              focus:outline-none focus:ring-2 focus:ring-blue-500
                                              focus:border-transparent transition-all duration-200
                                              bg-white placeholder-gray-400 font-mono"
                                       placeholder="e.g., CS101"
                                       required>
                            </div>
                            <p class="text-xs text-gray-500 mt-2 ml-1">Unique code for the subject</p>
                        </div>

                        <!-- Course Dropdown -->
                        <div>
                            <label for="editCourse" class="block text-sm font-medium text-gray-700 mb-2">
                                Course
                            </label>
                            <div class="relative">
                                <select name="courseId" id="editCourse"
                                        class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                           focus:outline-none focus:ring-2 focus:ring-blue-500
                                           focus:border-transparent transition-all duration-200
                                           bg-white appearance-none cursor-pointer
                                           text-gray-700 font-medium pr-10">
                                    <% if (!courses.isEmpty()) {
                                        for (Course c : courses) { %>

                                    <option value="<%= c.getId() %>">
                                        <%= c.getName() %>
                                    </option>

                                    <% }
                                    } else { %>

                                    <option value="" disabled selected>
                                        No course available
                                    </option>

                                    <% } %>
                                </select>
                                <div class="absolute inset-y-0 right-0 flex items-center px-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor"
                                         viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M19 9l-7 7-7-7"/>
                                    </svg>
                                </div>
                            </div>
                        </div>

                        <!-- Semester Dropdown -->
                        <div>
                            <label for="editSemester" class="block text-sm font-medium text-gray-700 mb-2">
                                Semester
                            </label>
                            <div class="relative">
                                <select name="semesterId" id="editSemester"
                                        class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                           focus:outline-none focus:ring-2 focus:ring-blue-500
                                           focus:border-transparent transition-all duration-200
                                           bg-white appearance-none cursor-pointer
                                           text-gray-700 font-medium pr-10">
                                    <option value="">-- Select Semester --</option>
                                </select>
                                <div class="absolute inset-y-0 right-0 flex items-center px-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor"
                                         viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M19 9l-7 7-7-7"/>
                                    </svg>
                                </div>
                            </div>
                        </div>

                        <!-- Assign Teacher Dropdown -->
                        <div>
                            <label for="editTeacher" class="block text-sm font-medium text-gray-700 mb-2">
                                Assign Teacher
                            </label>
                            <div class="relative">
                                <select name="teacherId" id="editTeacher"
                                        class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                           focus:outline-none focus:ring-2 focus:ring-blue-500
                                           focus:border-transparent transition-all duration-200
                                           bg-white appearance-none cursor-pointer
                                           text-gray-700 font-medium pr-10">
                                    <% if (teachers != null && !teachers.isEmpty()) {
                                        for (User t : teachers) { %>

                                    <option value="<%= t.getId() %>">
                                        <%= t.getName() %>
                                    </option>

                                    <% }
                                    } else { %>

                                    <option value="" disabled selected>
                                        No teachers available
                                    </option>

                                    <% } %>
                                </select>
                                <div class="absolute inset-y-0 right-0 flex items-center px-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor"
                                         viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M19 9l-7 7-7-7"/>
                                    </svg>
                                </div>
                            </div>
                            <p class="text-xs text-gray-500 mt-2 ml-1">Optional: Leave unassigned to assign later</p>
                        </div>

                        <!-- Submit Button -->
                        <div class="pt-4">
                            <button type="submit"
                                    class="w-full py-3 px-4 bg-gradient-to-r from-blue-600 to-indigo-600
                                           text-white font-semibold rounded-xl shadow-lg
                                           hover:from-blue-700 hover:to-indigo-700 hover:shadow-xl
                                           active:scale-[0.98] transition-all duration-200
                                           flex items-center justify-center space-x-2">
                                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M12 4v16m8-8H4"/>
                                </svg>
                                <span>Create Subject</span>
                            </button>
                        </div>

                    </form>

                </div>

            </div>
        </div>

    </main>

</div>

</body>
</html>