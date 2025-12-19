<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.riya.rms.models.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.User" %>
<%@ page import="com.riya.rms.models.Semester" %>
<%@ page import="com.riya.rms.models.Subject" %>
<%
    // Extract data from request attributes (set by servlet)
    List<User> teachers = (List<User>) request.getAttribute("teachers");
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
//    List<Semester> semesters = (List<Semester>) request.getAttribute("semesters");
//    int totalStudents = (int) request.getAttribute("totalStudents");

    // Set active menu for sidebar
    request.setAttribute("activeMenu", "teachers");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Teachers - Admin Panel</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>
    <script>
        const COURSE_DATA = {
            <% for (Course c : courses) { %>
            "<%= c.getId() %>": {
                semesters: {
                    <% for (Semester s : c.getSemesters()) { %>
                    "<%= s.getId() %>": {
                        name: "<%= s.getName() %>",
                        subjects: [
                            <% for (Subject sub : s.getSubjects()) { %>
                            {
                                id: "<%= sub.getId() %>",
                                name: "<%= sub.getName() %>"
                            },
                            <% } %>
                        ]
                    },
                    <% } %>
                }
            },
            <% } %>
        };
    </script>

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
                    <h1 class="text-3xl font-bold text-gray-900 mb-2">Teacher Management</h1>
                    <p class="text-gray-600">Manage all teachers and their details in one place</p>
                </div>

                <!-- Add New Course Button -->
                <%--            TODO:    Navigate to /admin/user/new-user?role=student --%>
                <a href="${pageContext.request.contextPath}/admin/user"
                   class="group relative inline-flex items-center justify-center overflow-hidden rounded-xl
                          bg-gradient-to-r from-blue-600 to-indigo-600 px-6 py-3 text-white font-semibold
                          shadow-lg transition-all duration-300 hover:shadow-xl hover:-translate-y-0.5
                          hover:from-blue-700 hover:to-indigo-700 active:scale-95">

                    <svg class="w-5 h-5 mr-2 group-hover:rotate-90 transition-transform duration-300"
                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M12 4v16m8-8H4"></path>
                    </svg>
                    Add new Teacher
                </a>
            </div>

            <!-- Stats Cards -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mt-6">
                <div class="bg-gradient-to-r from-blue-50 to-indigo-50 rounded-2xl px-4 py-3 border border-blue-100">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-sm font-medium text-gray-600">Total Teachers</p>
                            <p class="text-3xl font-bold text-gray-900 mt-2"><%= teachers.size() %>
                            </p>
                        </div>
                        <div class="h-12 w-12 rounded-xl bg-gradient-to-br from-blue-500 to-indigo-500
                                    flex items-center justify-center shadow-md text-white">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                 stroke-linejoin="round" class="lucide lucide-user-icon lucide-user">
                                <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/>
                                <circle cx="12" cy="7" r="4"/>
                            </svg>
                        </div>
                    </div>

                </div>


            </div>
        </div>

        <!-- Teachers Table Section -->
        <div class="bg-white rounded-2xl shadow-lg border border-gray-100 p-1">
            <div class="p-5 border-b border-gray-100">
                <div class="flex items-center justify-between">
                    <div>
                        <h2 class="text-lg font-semibold text-gray-900">All Teachers</h2>
                        <p class="text-sm text-gray-600 mt-1">List of all available teachers with details</p>
                    </div>
                </div>
            </div>

            <!-- Table -->
            <jsp:include page="teachers_table.jsp"/>


        </div>

    </main>

</div>

</body>
</html>