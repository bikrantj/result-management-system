<%@ page import="com.riya.rms.models.Course" %>
<%@ page import="java.util.List" %>

<%
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    request.setAttribute("activeMenu", "courses");
%>

<div class="rms-table-card">
    <table class="rms-table align-middle">
        <thead>
        <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Semesters</th>
            <th>Students</th>
            <th>Action</th>
        </tr>
        </thead>

        <tbody>
        <% if (courses != null && !courses.isEmpty()) {
            for (Course course : courses) { %>
        <tr>
            <td><%= course.getCode() %>
            </td>
            <td><%= course.getName() %>
            </td>
            <td><%= course.getSemesterCount() %>
            </td>
            <td><%= course.getTotalStudents() %>
            </td>
            <td>
                <form
                        action="<%= request.getContextPath() %>/admin/courses/delete"
                        method="post"
                        onsubmit="return confirm('Are you sure you want to delete this course? This action cannot be undone.');">

                    <input type="hidden" name="courseId" value="<%= course.getId() %>">
                    <button type="submit"
                            class="px-3 py-1.5 bg-red-50 text-red-700 rounded-lg text-sm hover:bg-red-100">
                        Delete
                    </button>
                </form>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="5" class="text-center py-8 text-gray-500">
                No courses found.
                <a href="<%= request.getContextPath() %>/admin/courses/new"
                   class="text-blue-600 hover:underline">
                    Create your first course
                </a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
