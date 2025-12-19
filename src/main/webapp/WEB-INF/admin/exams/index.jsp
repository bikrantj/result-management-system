<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.Course" %>
<%@ page import="com.riya.rms.models.Exam" %>

<%
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    List<Exam> exams = (List<Exam>) request.getAttribute("exams");

    request.setAttribute("activeMenu", "exams");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Exams - Admin Panel</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>

    <script>
        const COURSE_SEMESTERS = {
            <% for (Course c : courses) { %>
            "<%= c.getId() %>": [
                <% for (var s : c.getSemesters()) { %>
                {id: "<%= s.getId() %>", name: "<%= s.getName() %>"},
                <% } %>
            ],
            <% } %>
        };
    </script>
</head>

<body class="bg-gray-50">

<div class="flex min-h-screen">

    <jsp:include page="/WEB-INF/shared/sidebar-admin.jsp"/>

    <main class="flex-1 p-6">

        <!-- Header -->
        <div class="mb-8 flex justify-between items-center">
            <div>
                <h1 class="text-3xl font-bold">Exam Management</h1>
                <p class="text-gray-600">Create and manage exams</p>
            </div>

            <button data-bs-toggle="modal" data-bs-target="#createExamModal"
                    class="btn btn-primary">
                Create Exam
            </button>
        </div>

        <!-- Table -->
        <div class="bg-white rounded shadow">
            <jsp:include page="exam_table.jsp"/>
        </div>

    </main>
</div>

<jsp:include page="create-exam-modal.jsp"/>
<script src="${pageContext.request.contextPath}/js/create-exam.js"></script>

</body>
</html>
