<%@ page import="com.riya.rms.models.Course" %>
<%@ page import="java.util.List" %>

<%
    List<Course> courses = (List<Course>) request.getAttribute("courses");
%>

<div class="modal fade" id="createExamModal" tabindex="-1">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content rounded-2xl border-0 shadow-2xl overflow-hidden">

            <!-- Header -->
            <div class="modal-header bg-gradient-to-r from-blue-600 to-indigo-600 border-b-0 px-6">
                <div class="w-full flex justify-between items-center">
                    <div>
                        <h5 class="text-xl font-bold text-white">Create Exam</h5>
                        <p class="text-blue-100 text-sm">Define a new examination</p>
                    </div>
                    <button type="button" class="btn-close btn-close-white"
                            data-bs-dismiss="modal"></button>
                </div>
            </div>

            <!-- Form -->
            <form method="post" action="${pageContext.request.contextPath}/admin/exams/">
                <div class="modal-body p-6 space-y-5 bg-white">

                    <!-- Exam Name -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">
                            Exam Name
                        </label>
                        <input type="text" name="name" required
                               class="w-full px-4 py-3 border rounded-xl focus:ring-2 focus:ring-blue-500"
                               placeholder="Mid Term 2025">
                    </div>

                    <!-- Course -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">
                            Course
                        </label>
                        <select name="courseId" id="examCourse"
                                class="w-full px-4 py-3 border rounded-xl">
                            <option value="">-- Select Course --</option>
                            <% for (Course c : courses) { %>
                            <option value="<%= c.getId() %>">
                                <%= c.getName() %>
                            </option>
                            <% } %>
                        </select>
                    </div>

                    <!-- Semester -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">
                            Semester
                        </label>
                        <select name="semesterId" id="examSemester"
                                class="w-full px-4 py-3 border rounded-xl">
                            <option value="">-- Select Semester --</option>
                        </select>
                    </div>


                </div>

                <!-- Footer -->
                <div class="modal-footer bg-gray-50 border-t px-6 py-4">
                    <button type="button" class="px-4 py-2 border rounded-xl"
                            data-bs-dismiss="modal">
                        Cancel
                    </button>
                    <button type="submit"
                            class="px-5 py-2 bg-gradient-to-r from-blue-600 to-indigo-600
                                   text-white rounded-xl font-semibold">
                        Create Exam
                    </button>
                </div>
            </form>

        </div>
    </div>
</div>
