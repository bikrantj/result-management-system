<%--Admin will create new user accounts: for Teachers and Students in this page--%>

<%
    // Extract all message variables at the top
    String errorMessage = (String) session.getAttribute("error");
    String successMessage = (String) session.getAttribute("success");

    // Clear session attributes after extracting
    if (errorMessage != null) session.removeAttribute("error");
    if (successMessage != null) session.removeAttribute("success");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Students - Admin Panel</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>
</head>

<body class="bg-gray-50 font-sans antialiased flex items-center justify-center min-h-screen">

<div class="w-full max-w-lg bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden">
    <!-- Header -->
    <div class="bg-gradient-to-r from-blue-600 to-indigo-600 p-6">
        <div class="flex items-center justify-between">
            <div>
                <h2 class="text-2xl font-bold text-white">Add New User</h2>
                <p class="text-blue-100 text-sm mt-1">Create a new user account</p>
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


        <form action="${pageContext.request.contextPath}/admin/create-new-user" method="post" class="space-y-6">


            <div>
                <label for="role" class="block text-sm font-medium text-gray-700 mb-2">
                    Role
                </label>
                <div class="relative">
                    <select id="role" name="role"
                            class="w-full px-4 py-3 border border-gray-200 rounded-xl
                       focus:outline-none focus:ring-2 focus:ring-blue-500
                       focus:border-transparent transition-all duration-200
                       bg-white appearance-none cursor-pointer
                       text-gray-700 font-medium
                       hover:border-gray-300 hover:bg-gray-50
                       shadow-sm">
                        <option value="student" class="py-2" selected>Student</option>
                        <option value="teacher" class="py-2">Teacher</option>
                    </select>
                    <!-- Custom dropdown arrow -->
                    <div class="absolute inset-y-0 right-0 flex items-center px-3 pointer-events-none">
                        <svg class="w-5 h-5 text-gray-400 transform transition-transform duration-200"
                             fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M19 9l-7 7-7-7"/>
                        </svg>
                    </div>
                </div>
            </div>
            <div>
                <label for="fullName" class="block text-sm font-medium text-gray-700 mb-2">
                    Full Name
                </label>
                <div class="relative">
                    <input type="text"
                           name="fullName"
                           id="fullName"
                           class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                      focus:outline-none focus:ring-2 focus:ring-blue-500
                                      focus:border-transparent transition-all duration-200
                                      bg-gray-50 placeholder-gray-400"
                           required>

                </div>
            </div>

            <div>
                <label for="username" class="block text-sm font-medium text-gray-700 mb-2">
                    Username
                </label>
                <div class="relative">
                    <input type="text"
                           name="username"
                           id="username"
                           class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                      focus:outline-none focus:ring-2 focus:ring-blue-500
                                      focus:border-transparent transition-all duration-200
                                      bg-gray-50 placeholder-gray-400 font-mono"
                           required>
                </div>
            </div>

            <div>
                <label for="password" class="block text-sm font-medium text-gray-700 mb-2">
                    Default Password
                </label>
                <div class="relative">
                    <input type="text"
                           name="password"
                           id="password"
                           class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                      focus:outline-none focus:ring-2 focus:ring-blue-500
                                      focus:border-transparent transition-all duration-200
                                      bg-gray-50 placeholder-gray-400"
                           value="12345678"
                           required>
                </div>

            </div>

            <div class="pt-4">
                <button type="submit"
                        class="w-full py-3 px-4 bg-gradient-to-r from-blue-600 to-indigo-600
                                   text-white font-semibold rounded-xl shadow-lg
                                   hover:from-blue-700 hover:to-indigo-700 hover:shadow-xl
                                   active:scale-[0.98] transition-all duration-200
                                   flex items-center justify-center space-x-2">

                    <span>Create Account</span>
                </button>
            </div>

        </form>

    </div>

</div>
</body>
</html>