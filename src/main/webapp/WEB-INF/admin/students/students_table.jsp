<%@ page import="com.riya.rms.models.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.User" %>
<%
    // Extract data from request attributes (set by servlet)
    List<User> students = (List<User>) request.getAttribute("users");

    // Set active menu for sidebar
    request.setAttribute("activeMenu", "courses");
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
            <td><%= student.getUsername()  %>
            </td>
            <td><%= student.getName() %>
            </td>
            <td>
                <%= (student.getCourse() != null)
                        ? student.getCourse().getCode()
                        : "-" %>
            </td>
            <td>
                <%= (student.getSemester() != null)
                        ? student.getSemester().getName()
                        : "-" %>
            </td>
            <td class="text-center">
                <button
                        type="button"
                        class="edit-student-btn text-blue-500 hover:text-blue-800"
                        data-bs-toggle="modal"
                        data-bs-target="#editStudentModal"

                        data-id="<%= student.getId() %>"
                        data-username="<%= student.getUsername() %>"
                        data-name="<%= student.getName() %>"
                        data-course-id="<%= student.getCourse().getId() %>"
                        data-semester-id="<%= student.getSemester().getId() %>">

                    Edit
                </button>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="5" class="text-center py-8 text-gray-500">
                No students found. <a href="${pageContext.request.contextPath}/WEB-INF/admin/courses/new-course.jsp"
                                      class="text-blue-600 hover:underline">Create your first user</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<jsp:include page="edit-student-modal.jsp"/>
<script src="${pageContext.request.contextPath}/js/student-edit.js"></script>
