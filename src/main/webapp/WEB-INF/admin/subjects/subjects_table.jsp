<%@ page import="com.riya.rms.models.Subject" %>
<%@ page import="java.util.List" %>

<%
    // Extract data from request attributes (set by servlet)
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");

    // Set active menu for sidebar
    request.setAttribute("activeMenu", "subjects");
%>

<div class="rms-table-card">
    <table class="rms-table align-middle">
        <thead>
        <tr>
            <th>ID</th>
            <th>Code</th>
            <th>Name</th>
            <th>Course</th>
            <th>Semester</th>
            <th>Assigned Teacher</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <% if (subjects != null && !subjects.isEmpty()) {
            for (Subject subject : subjects) { %>

        <tr>
            <td><%= subject.getId() %>
            </td>

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
                <div class="flex gap-2">
                    <button
                            class="px-3 py-1.5 bg-blue-50 text-blue-700 rounded-lg text-sm
                                   hover:bg-blue-100 transition-colors edit-subject-btn"
                            data-id="<%= subject.getId() %>">
                        Edit
                    </button>

                    <button
                            class="px-3 py-1.5 bg-red-50 text-red-700 rounded-lg text-sm
                                   hover:bg-red-100 transition-colors delete-subject-btn"
                            data-id="<%= subject.getId() %>">
                        Delete
                    </button>
                </div>
            </td>
        </tr>

        <% }
        } else { %>

        <tr>
            <td colspan="7" class="text-center py-8 text-gray-500">
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
