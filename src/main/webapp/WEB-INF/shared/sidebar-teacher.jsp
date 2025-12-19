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
                Teacher Panel
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
                <a href="${pageContext.request.contextPath}/teacher/dashboard"
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

        </ul>
    </nav>

    <!-- Bottom Section -->
    <div class="mt-auto pt-6 border-t border-gray-100">
        <jsp:include page="logout.jsp"/>
    </div>

</div>