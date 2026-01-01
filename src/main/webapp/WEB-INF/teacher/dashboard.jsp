<%@ page import="com.riya.rms.models.Exam" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Exam> exams = (List<Exam>) request.getAttribute("exams");
    request.setAttribute("activeMenu", "dashboard");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>
</head>

<body class="bg-gray-50 font-sans antialiased">

<div class="flex min-h-screen">

    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/shared/sidebar-teacher.jsp"/>

    <!-- Main Content -->
    <main class="flex-1 p-6">

        <!-- Page Header -->
        <div class="mb-8">
            <h1 class="text-3xl font-bold text-gray-900 mb-2">Ongoing Exams</h1>
            <p class="text-gray-600">Select an exam to enter marks</p>
        </div>

        <!-- Exam Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

            <% if (exams != null && !exams.isEmpty()) {
                for (Exam exam : exams) { %>

            <a href="${pageContext.request.contextPath}/teacher/exams/<%= exam.getId() %>"
               class="group block bg-white rounded-2xl border border-gray-100 shadow-sm
                      hover:shadow-xl hover:-translate-y-1 transition-all duration-300 p-6">

                <div class="flex items-center justify-between mb-4">
                    <h2 class="text-lg font-semibold text-gray-900 group-hover:text-blue-600">
                        <%= exam.getName() %>
                    </h2>
                    <span class="px-3 py-1 rounded-full text-sm font-medium
                                 bg-blue-50 text-blue-600">
                        Active
                    </span>
                </div>

                <div class="space-y-2 text-sm text-gray-600">
                    <p>
                        <span class="font-medium text-gray-700">Course:</span>
                        <%= exam.getCourseName() %>
                    </p>
                    <p>
                        <span class="font-medium text-gray-700">Semester:</span>
                        <%= exam.getSemesterName() %>
                    </p>
                    
                </div>

                <div class="mt-5 flex items-center text-blue-600 font-medium text-sm">
                    Enter Marks
                    <svg class="w-4 h-4 ml-2 group-hover:translate-x-1 transition-transform"
                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M9 5l7 7-7 7"/>
                    </svg>
                </div>
            </a>

            <% }
            } else { %>

            <div class="col-span-full text-center py-16 text-gray-500">
                No exams available for you right now.
            </div>

            <% } %>

        </div>

    </main>
</div>

</body>
</html>
