<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.User" %>

<%
    // Teachers list provided by servlet
    List<User> teachers = (List<User>) request.getAttribute("teachers");

    // Sidebar highlight
    request.setAttribute("activeMenu", "teachers");
%>

<div class="rms-table-card">
    <table class="rms-table align-middle">
        <thead>
        <tr>
            <th>Username</th>
            <th>Full Name</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <% if (teachers != null && !teachers.isEmpty()) {
            for (User teacher : teachers) { %>
        <tr>
            <td><%= teacher.getUsername() %>
            </td>
            <td><%= teacher.getName() %>
            </td>
            <td><%= teacher.getRole() %>
            </td>

            <td>
                <button
                        type="button"
                        style="margin-right: 10px;"
                        class="edit-teacher-btn text-blue-500 hover:text-blue-800"
                        data-bs-toggle="modal"
                        data-bs-target="#editTeacherModal"

                        data-id="<%= teacher.getId() %>"
                        data-username="<%= teacher.getUsername() %>"
                        data-name="<%= teacher.getName() %>"
                        data-course-id="<%= (teacher.getCourse() != null) ? teacher.getCourse().getId() : "" %>"
                        data-semester-id="<%= (teacher.getSemester() != null) ? teacher.getSemester().getId() : "" %>"
                        data-subject-id="<%= (teacher.getSubject() != null) ? teacher.getSubject().getId() : "" %>">

                    Edit
                </button>

                <a href="${pageContext.request.contextPath}/admin/teachers/?showSubjects=true&teacherId=<%= teacher.getId() %>"
                   class="text-indigo-600 hover:text-indigo-900">
                    Show Subjects
                </a>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="4" class="text-center py-8 text-gray-500">
                No teachers found.
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<jsp:include page="teacher-subject-modal.jsp"/>
<script src="${pageContext.request.contextPath}/js/teacher-subjects.js"></script>

<jsp:include page="edit-teacher-modal.jsp"/>
<script src="${pageContext.request.contextPath}/js/teacher-edit.js"></script>
