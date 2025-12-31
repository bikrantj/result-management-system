<%@ page import="com.riya.rms.models.Subject" %>
<%@ page import="java.util.List" %>

<%
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
    request.setAttribute("activeMenu", "subjects");
%>

<div class="rms-table-card">
    <table class="rms-table align-middle">
        <thead>
        <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Course</th>
            <th>Semester</th>
            <th>Assigned Teacher</th>
            <th>Action</th>
        </tr>
        </thead>

        <tbody>
        <% if (subjects != null && !subjects.isEmpty()) {
            for (Subject subject : subjects) { %>
        <tr>
            <td><%= subject.getCode() %>
            </td>
            <td><%= subject.getName() %>
            </td>
            <td><%= subject.getCourseName() %>
            </td>
            <td><%= subject.getSemesterName() %>
            </td>
            <td>
                <%= subject.getAssignedTeacherName() != null
                        ? subject.getAssignedTeacherName()
                        : "Not Assigned" %>
            </td>
            <td>
                <form
                        action="<%= request.getContextPath() %>/admin/subjects/delete"
                        method="post"
                        onsubmit="return confirm('Delete this subject? This action cannot be undone.');">

                    <input type="hidden" name="subjectId" value="<%= subject.getId() %>">
                    <button class="px-3 py-1.5 bg-red-50 text-red-700 rounded-lg text-sm hover:bg-red-100">
                        Delete
                    </button>
                </form>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="6" class="text-center py-8 text-gray-500">
                No subjects found.
                <a href="<%= request.getContextPath() %>/admin/subjects/new"
                   class="text-blue-600 hover:underline">
                    Create your first subject
                </a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
