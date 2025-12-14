<%
    String activeMenu = (String) request.getAttribute("activeMenu");
    String current = activeMenu == null ? "" : activeMenu;
%>

<div class="h-screen sticky top-0 w-72 bg-gradient-to-b from-white to-gray-50/30 border-r border-gray-100 p-4 flex flex-col shadow-lg shadow-gray-200/50 backdrop-blur-sm">

    <!-- Logo/Header Section -->
    <div class="mb-8 px-2">
        <div class="flex items-center justify-start space-x-3">
            <div class="h-10 w-10 rounded-lg bg-gradient-to-br from-blue-500 to-indigo-600 flex items-center justify-center shadow-md">
                <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                </svg>
            </div>
            <h1 class="text-xl font-bold bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">
                Admin Panel
            </h1>
        </div>
        <p class="text-xs text-gray-500 mt-2 ml-1 font-medium tracking-wide">
            Management Console
        </p>
    </div>

    <!-- Divider -->
    <div class="h-px bg-gradient-to-r from-transparent via-gray-200 to-transparent mb-8"></div>

    <!-- Navigation Menu -->
    <nav class="flex-1">
        <ul class="space-y-2">

            <li>
                <a href="${pageContext.request.contextPath}/admin/dashboard"
                   class="group flex items-center px-4 py-3 rounded-xl transition-all duration-200
                   <%= "dashboard".equals(current)
                        ? "bg-gradient-to-r from-blue-50 to-indigo-50 text-blue-700 font-semibold border border-blue-100 shadow-sm shadow-blue-100/50"
                        : "text-gray-600 hover:bg-gray-50/80 hover:text-gray-900 hover:shadow-sm" %>">
                    <svg class="w-5 h-5 mr-3 <%= "dashboard".equals(current) ? "text-blue-500" : "text-gray-400 group-hover:text-blue-500" %>"
                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round"
                              stroke-width="<%= "dashboard".equals(current) ? "2" : "1.5" %>"
                              d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
                    </svg>
                    Dashboard
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/admin/courses/"
                   class="group flex items-center px-4 py-3 rounded-xl transition-all duration-200
                   <%= "courses".equals(current)
                        ? "bg-gradient-to-r from-blue-50 to-indigo-50 text-blue-700 font-semibold border border-blue-100 shadow-sm shadow-blue-100/50"
                        : "text-gray-600 hover:bg-gray-50/80 hover:text-gray-900 hover:shadow-sm" %>">
                    <svg class="w-5 h-5 mr-3 <%= "courses".equals(current) ? "text-blue-500" : "text-gray-400 group-hover:text-blue-500" %>"
                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round"
                              stroke-width="<%= "courses".equals(current) ? "2" : "1.5" %>"
                              d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
                    </svg>
                    Courses
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/subjects/"
                   class="group flex items-center px-4 py-3 rounded-xl transition-all duration-200
                   <%= "subjects".equals(current)
                        ? "bg-gradient-to-r from-blue-50 to-indigo-50 text-blue-700 font-semibold border border-blue-100 shadow-sm shadow-blue-100/50"
                        : "text-gray-600 hover:bg-gray-50/80 hover:text-gray-900 hover:shadow-sm" %>">
                    <svg class="w-5 h-5 mr-3 <%= "courses".equals(current) ? "text-blue-500" : "text-gray-400 group-hover:text-blue-500" %>"
                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round"
                              stroke-width="<%= "courses".equals(current) ? "2" : "1.5" %>"
                              d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
                    </svg>
                    Subjects
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/admin/students/"
                   class="group flex items-center px-4 py-3 rounded-xl transition-all duration-200
                   <%= "students".equals(current)
                        ? "bg-gradient-to-r from-blue-50 to-indigo-50 text-blue-700 font-semibold border border-blue-100 shadow-sm shadow-blue-100/50"
                        : "text-gray-600 hover:bg-gray-50/80 hover:text-gray-900 hover:shadow-sm" %>">
                    <svg class="w-5 h-5 mr-3 <%= "students".equals(current) ? "text-blue-500" : "text-gray-400 group-hover:text-blue-500" %>"
                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round"
                              stroke-width="<%= "students".equals(current) ? "2" : "1.5" %>"
                              d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13.5 0h-6"></path>
                    </svg>
                    Students
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/admin/teachers/"
                   class="group flex items-center px-4 py-3 rounded-xl transition-all duration-200
                   <%= "teachers".equals(current)
                        ? "bg-gradient-to-r from-blue-50 to-indigo-50 text-blue-700 font-semibold border border-blue-100 shadow-sm shadow-blue-100/50"
                        : "text-gray-600 hover:bg-gray-50/80 hover:text-gray-900 hover:shadow-sm" %>">
                    <svg class="w-5 h-5 mr-3 <%= "teachers".equals(current) ? "text-blue-500" : "text-gray-400 group-hover:text-blue-500" %>"
                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round"
                              stroke-width="<%= "teachers".equals(current) ? "2" : "1.5" %>"
                              d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                    </svg>
                    Teachers
                </a>
            </li>

        </ul>
    </nav>

    <!-- Bottom Section -->
    <div class="mt-auto pt-6 border-t border-gray-100">
        <div class="px-4 py-3 bg-gradient-to-r from-gray-50 to-gray-100/50 rounded-xl border border-gray-200/50">
            <p class="text-xs text-gray-500 font-medium">System Status</p>
            <div class="flex items-center mt-1">
                <div class="h-2 w-2 rounded-full bg-green-500 mr-2 animate-pulse"></div>
                <p class="text-sm text-gray-700 font-medium">All systems operational</p>
            </div>
        </div>
    </div>

</div>