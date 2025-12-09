<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Tell sidebar which menu is active
    request.setAttribute("activeMenu", "courses");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Courses</title>
    <%@ include file="/shared/head.jsp" %>

</head>

<body class="m-0 p-0">

<div style="display:flex; min-height:100vh;">

    <!-- Sidebar -->
    <jsp:include page="/shared/sidebar-admin.jsp"/>

    <!-- Main Content -->
    <div style="flex:1; background:#f5f5f5;padding:20px;" class="main-content-area">
        <div class="flex-row mb-4 d-flex justify-content-between align-items-center">

            <h1>Course Management</h1>
            <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addCourseModal">
                Add New Course
            </button>
        </div>
        <jsp:include page="add_new_course.jsp"/>
        <jsp:include page="courses_table.jsp"/>

        <%--       Use Directly add_new_course and course_table here--%>
    </div>

</div>

</body>
</html>
