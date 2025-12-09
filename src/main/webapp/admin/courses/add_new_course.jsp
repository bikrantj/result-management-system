<!-- Remove the outer container div that had min-h-screen -->
<%
    // Extract all message variables at the top
    String errorMessage = (String) session.getAttribute("error");
    String successMessage = (String) session.getAttribute("success");

    // Clear session attributes after extracting
    if (errorMessage != null) session.removeAttribute("error");
    if (successMessage != null) session.removeAttribute("success");
%>
<div class="w-full max-w-lg bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden">
    <!-- Header -->
    <div class="bg-gradient-to-r from-blue-600 to-indigo-600 p-6">
        <div class="flex items-center justify-between">
            <div>
                <h2 class="text-2xl font-bold text-white">Add New Course</h2>
                <p class="text-blue-100 text-sm mt-1">Create a new academic course</p>
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


        <form action="${pageContext.request.contextPath}/course" method="post" class="space-y-6">

            <!-- Course Name Field -->
            <div>
                <label for="courseName" class="block text-sm font-medium text-gray-700 mb-2">
                    Course Name
                </label>
                <div class="relative">
                    <input type="text"
                           name="courseName"
                           id="courseName"
                           class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                      focus:outline-none focus:ring-2 focus:ring-blue-500
                                      focus:border-transparent transition-all duration-200
                                      bg-gray-50 placeholder-gray-400"
                           placeholder="e.g., Bachelor in Information Technology"
                           required>

                </div>
            </div>

            <!-- Course Code Field -->
            <div>
                <label for="courseCode" class="block text-sm font-medium text-gray-700 mb-2">
                    Course Code
                </label>
                <div class="relative">
                    <input type="text"
                           name="courseCode"
                           id="courseCode"
                           class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                      focus:outline-none focus:ring-2 focus:ring-blue-500
                                      focus:border-transparent transition-all duration-200
                                      bg-gray-50 placeholder-gray-400 font-mono"
                           placeholder="e.g., BIT"
                           required>
                </div>
            </div>

            <!-- Semester Count Field -->
            <div>
                <label for="semesterCount" class="block text-sm font-medium text-gray-700 mb-2">
                    Number of Semesters
                </label>
                <div class="relative">
                    <input type="number"
                           name="semesterCount"
                           id="semesterCount"
                           class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                      focus:outline-none focus:ring-2 focus:ring-blue-500
                                      focus:border-transparent transition-all duration-200
                                      bg-gray-50 placeholder-gray-400"
                           min="1"
                           max="12"
                           value="8"
                           required>
                </div>

            </div>

            <!-- Submit Button -->
            <div class="pt-4">
                <button type="submit"
                        class="w-full py-3 px-4 bg-gradient-to-r from-blue-600 to-indigo-600
                                   text-white font-semibold rounded-xl shadow-lg
                                   hover:from-blue-700 hover:to-indigo-700 hover:shadow-xl
                                   active:scale-[0.98] transition-all duration-200
                                   flex items-center justify-center space-x-2">

                    <span>Add Course</span>
                </button>
            </div>

        </form>

    </div>

</div>