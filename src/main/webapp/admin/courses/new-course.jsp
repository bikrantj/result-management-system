<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Tell sidebar which menu is active
    request.setAttribute("activeMenu", "courses");
%>

<!DOCTYPE html>
<html>
<head>
    <title>New Course - Admin Panel</title>
    <%@ include file="/shared/head.jsp" %>
</head>

<body class="bg-gray-50">

<div class="flex min-h-screen">

    <!-- Sidebar -->
    <jsp:include page="/shared/sidebar-admin.jsp"/>

    <!-- Main Content -->
    <main class="flex-1 p-6">

        <!-- Simple Header with Back Button -->
        <div class="mb-6">
            <div class="flex items-center gap-4">
                <a href="${pageContext.request.contextPath}/admin/courses/index.jsp">

                    <button
                            class="px-4 py-2 rounded-lg border border-gray-300 bg-white text-gray-700
                               font-medium hover:bg-gray-50 transition-colors">
                        ← Back
                    </button>
                </a>
                <div>
                    <h1 class="text-2xl font-bold text-gray-900">Add New Course</h1>
                    <p class="text-gray-600 mt-1">Create a new academic course</p>
                </div>
            </div>
        </div>

        <!-- Form Container (centered) -->
        <div class="flex items-center justify-center min-h-[calc(100vh-80px)] bg-gray-50">
            <jsp:include page="add_new_course.jsp"/>
        </div>

    </main>

</div>

</body>
</html>