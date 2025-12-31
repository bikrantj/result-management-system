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

            <td class="text-center">
                <div class="flex gap-3 justify-center">

                    <!-- EDIT BUTTON -->
                    <button
                            type="button"
                            class="px-3 py-1.5 rounded-md bg-blue-600 text-white text-sm
                   hover:bg-blue-700 transition-colors
                   edit-teacher-btn"
                            data-bs-toggle="modal"
                            data-bs-target="#editTeacherModal"

                            data-id="<%= teacher.getId() %>"
                            data-username="<%= teacher.getUsername() %>"
                            data-name="<%= teacher.getName() %>"
                            data-course-id="<%= teacher.getCourse() != null ? teacher.getCourse().getId() : "" %>"
                            data-semester-id="<%= teacher.getSemester() != null ? teacher.getSemester().getId() : "" %>"
                            data-subject-id="<%= teacher.getSubject() != null ? teacher.getSubject().getId() : "" %>">
                        Edit
                    </button>

                    <!-- SHOW SUBJECTS BUTTON -->
                    <a
                            href="<%= request.getContextPath() %>/admin/teachers/?showSubjects=true&teacherId=<%= teacher.getId() %>"
                            class="px-3 py-1.5 rounded-md bg-indigo-600 text-white text-sm
                   hover:bg-indigo-700 transition-colors
                   inline-flex items-center">
                        Show Subjects
                    </a>

                    <!-- DELETE BUTTON -->
                    <form
                            action="<%= request.getContextPath() %>/admin/teachers/delete"
                            method="post"
                            class="inline"
                            onsubmit="return confirm('Delete this teacher? Assigned subjects will be unassigned.');">

                        <input type="hidden" name="teacherId" value="<%= teacher.getId() %>">

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
