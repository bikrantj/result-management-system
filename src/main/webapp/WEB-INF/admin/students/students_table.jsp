<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.User" %>

<%
    List<User> students = (List<User>) request.getAttribute("users");
    request.setAttribute("activeMenu", "students");
%>

<div class="rms-table-card">
    <table class="rms-table align-middle">
        <thead>
        <tr>
            <th>Username</th>
            <th>Full Name</th>
            <th>Course</th>
            <th>Semester</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <% if (students != null && !students.isEmpty()) {
            for (User student : students) { %>
        <tr>
            <td><%= student.getUsername() %>
            </td>
            <td><%= student.getName() %>
            </td>

            <td>
                <%= student.getCourse() != null
                        ? student.getCourse().getCode()
                        : "-" %>
            </td>

            <td>
                <%= student.getSemester() != null
                        ? student.getSemester().getName()
                        : "-" %>
            </td>

            <td class="text-center">
                <div class="flex gap-3 ">

                    <!-- EDIT BUTTON -->
                    <button
                            type="button"
                            class="px-3 py-1.5 rounded-md bg-blue-600 text-white text-sm
                   hover:bg-blue-700 transition-colors
                   edit-student-btn"
                            data-bs-toggle="modal"
                            data-bs-target="#editStudentModal"

                            data-id="<%= student.getId() %>"
                            data-username="<%= student.getUsername() %>"
                            data-name="<%= student.getName() %>"
                            data-course-id="<%= student.getCourse() != null ? student.getCourse().getId() : "" %>"
                            data-semester-id="<%= student.getSemester() != null ? student.getSemester().getId() : "" %>">
                        Edit
                    </button>

                    <!-- DELETE BUTTON -->
                    <form
                            action="<%= request.getContextPath() %>/admin/students/delete"
                            method="post"
                            class="inline"
                            onsubmit="return confirm('Delete this student? Academic records may be affected.');">

                        <input type="hidden" name="studentId" value="<%= student.getId() %>">

                        <button
                                type="submit"
                                class="px-3 py-1.5 rounded-md bg-red-600 text-white text-sm
                       hover:bg-red-700 transition-colors">
                            Delete
                        </button>
                    </form>

                </div>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="5" class="text-center py-8 text-gray-500">
                No students found.
                <a href="<%= request.getContextPath() %>/admin/students/new"
                   class="text-blue-600 hover:underline">
                    Create your first student
                </a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<jsp:include page="edit-student-modal.jsp"/>
<script src="<%= request.getContextPath() %>/js/student-edit.js"></script>
