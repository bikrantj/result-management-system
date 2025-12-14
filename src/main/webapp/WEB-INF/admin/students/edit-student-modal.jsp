<%@ page import="com.riya.rms.models.Course" %>
<%@ page import="java.util.List" %>
<%
    // Extract data from request attributes (set by servlet)
    List<Course> courses = (List<Course>) request.getAttribute("courses");
%>

<div class="modal fade" id="editStudentModal" tabindex="-1">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content rounded-2xl border-0 shadow-2xl overflow-hidden">

            <!-- Modal Header with gradient background -->
            <div class="modal-header bg-gradient-to-r from-blue-600 to-indigo-600 border-b-0 px-6">
                <div class="flex items-center justify-between w-full">
                    <div class="flex items-center gap-3">
                        <div>
                            <h5 class="modal-title text-xl font-bold text-white mb-0">Edit Student</h5>
                            <p class="text-blue-100 text-sm mt-1">Update student information</p>
                        </div>
                    </div>
                    <button type="button"
                            class="btn-close !text-white opacity-80 hover:opacity-100 transition-opacity
                                   !outline-none !ring-0 focus:!outline-none focus:!ring-0"
                            data-bs-dismiss="modal">
                        <span class="sr-only">Close</span>
                    </button>
                </div>
            </div>

            <!-- Modal Body -->
            <form method="post" action="${pageContext.request.contextPath}/admin/students/">
                <div class="modal-body space-y-5 p-6 bg-gradient-to-b from-white to-gray-50/30">

                    <input type="hidden" name="id" id="editStudentId"/>

                    <!-- Username Field -->
                    <div>
                        <label for="editUsername" class="block text-sm font-medium text-gray-700 mb-2">
                            Username
                        </label>
                        <div class="relative">
                            <input type="text" name="username" id="editUsername"
                                   class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                          focus:outline-none focus:ring-2 focus:ring-blue-500
                                          focus:border-transparent transition-all duration-200
                                          bg-gray-100 text-gray-500 cursor-not-allowed
                                          font-mono"
                            >
                            <div class="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
                                <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                                </svg>
                            </div>
                        </div>
                    </div>

                    <!-- Full Name Field -->
                    <div>
                        <label for="editName" class="block text-sm font-medium text-gray-700 mb-2">
                            Full Name
                        </label>
                        <div class="relative">
                            <input type="text" name="fullName" id="editName"
                                   class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                          focus:outline-none focus:ring-2 focus:ring-blue-500
                                          focus:border-transparent transition-all duration-200
                                          bg-white placeholder-gray-400"
                                   placeholder="Enter student's full name">
                            <div class="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
                                <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                                </svg>
                            </div>
                        </div>
                    </div>

                    <!-- Course Dropdown -->
                    <div>
                        <label for="editCourse" class="block text-sm font-medium text-gray-700 mb-2">
                            Course
                        </label>
                        <div class="relative">
                            <select name="courseId" id="editCourse"
                                    class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                           focus:outline-none focus:ring-2 focus:ring-blue-500
                                           focus:border-transparent transition-all duration-200
                                           bg-white appearance-none cursor-pointer
                                           text-gray-700 font-medium pr-10">
                                <% if (courses != null && !courses.isEmpty()) {
                                    for (Course c : courses) { %>
                                <option value="<%= c.getId() %>">
                                    <%= c.getName() %>
                                </option>
                                <% }
                                } %>
                            </select>
                            <div class="absolute inset-y-0 right-0 flex items-center px-3 pointer-events-none">
                                <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M19 9l-7 7-7-7"/>
                                </svg>
                            </div>
                        </div>
                    </div>

                    <!-- Semester Dropdown -->
                    <div>
                        <label for="editSemester" class="block text-sm font-medium text-gray-700 mb-2">
                            Semester
                        </label>
                        <div class="relative">
                            <select name="semesterId" id="editSemester"
                                    class="w-full px-4 py-3 border border-gray-200 rounded-xl
                                           focus:outline-none focus:ring-2 focus:ring-blue-500
                                           focus:border-transparent transition-all duration-200
                                           bg-white appearance-none cursor-pointer
                                           text-gray-700 font-medium pr-10">
                                <option value="">-- Select Semester --</option>
                            </select>
                            <div class="absolute inset-y-0 right-0 flex items-center px-3 pointer-events-none">
                                <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M19 9l-7 7-7-7"/>
                                </svg>
                            </div>
                        </div>
                        <p class="text-xs text-gray-500 mt-2 ml-1">Select course first to load semesters</p>
                    </div>

                </div>

                <!-- Modal Footer -->
                <div class="modal-footer border-t border-gray-100 bg-gray-50 px-6 py-4">
                    <div class="flex items-center justify-end gap-3 w-full">
                        <button type="button"
                                class="px-5 py-2.5 border border-gray-300 rounded-xl
                                       bg-white text-gray-700 font-medium
                                       hover:bg-gray-50 hover:border-gray-400
                                       transition-all duration-200 shadow-sm"
                                data-bs-dismiss="modal">
                            Cancel
                        </button>
                        <button type="submit"
                                class="px-5 py-2.5 bg-gradient-to-r from-blue-600 to-indigo-600
                                       text-white font-semibold rounded-xl shadow-lg
                                       hover:from-blue-700 hover:to-indigo-700 hover:shadow-xl
                                       active:scale-[0.98] transition-all duration-200
                                       flex items-center gap-2">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M5 13l4 4L19 7"/>
                            </svg>
                            Update Student
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>