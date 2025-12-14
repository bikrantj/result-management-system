<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Set active menu for sidebar
    request.setAttribute("activeMenu", "subjects");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Subjects - Admin Panel</title>
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
                    <h1 class="text-3xl font-bold text-gray-900 mb-2">Subject Management <span
                            class="text-xl text-blue-600">(3 subjects)</span></h1>
                    <p class="text-gray-600">Manage all subjects and their assignments in one place</p>
                </div>

                <!-- Add New Subject Button -->
                <a href="${pageContext.request.contextPath}/admin/subjects/new-subject"
                   class="group relative inline-flex items-center justify-center overflow-hidden rounded-xl
                          bg-gradient-to-r from-blue-600 to-indigo-600 px-6 py-3 text-white font-semibold
                          shadow-lg transition-all duration-300 hover:shadow-xl hover:-translate-y-0.5
                          hover:from-blue-700 hover:to-indigo-700 active:scale-95">

                    <svg class="w-5 h-5 mr-2 group-hover:rotate-90 transition-transform duration-300"
                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M12 4v16m8-8H4"></path>
                    </svg>
                    Add New Subject
                </a>
            </div>

            <!-- Subjects Table Section -->
            <div class="bg-white rounded-2xl shadow-lg border border-gray-100 p-1">
                <div class="px-4 py-3 border-b border-gray-100">
                    <div class="flex items-center justify-between">
                        <div>
                            <h2 class="text-lg font-semibold text-gray-900">All Subjects</h2>
                            <p class="text-sm text-gray-600 mt-1">List of all subjects with course and teacher
                                assignments</p>
                        </div>
                        <div class="flex items-center space-x-3">
                            <div class="relative">
                                <input type="text"
                                       placeholder="Search subjects..."
                                       class="pl-10 pr-4 py-2 border border-gray-200 rounded-xl
                                          focus:outline-none focus:ring-2 focus:ring-blue-500
                                          focus:border-transparent bg-gray-50 w-64">
                                <svg class="w-5 h-5 text-gray-400 absolute left-3 top-1/2 transform -translate-y-1/2"
                                     fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                                </svg>
                            </div>
                            <select class="px-4 py-2 border border-gray-200 rounded-xl text-gray-700
                                      hover:bg-gray-50 transition-colors bg-white">
                                <option>All Courses</option>
                                <option>BIT</option>
                                <option>BCA</option>
                                <option>BE</option>
                            </select>
                        </div>
                    </div>
                </div>

                <!-- Table -->
                <jsp:include page="subjects_table.jsp"/>

            </div>
        </div>
    </main>

</div>

</body>
</html>