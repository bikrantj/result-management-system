<%@ page import="com.riya.rms.models.Course" %>
<%@ page import="java.util.List" %>
<%
    // Extract data from request attributes (set by servlet)
    List<Course> courses = (List<Course>) request.getAttribute("courses");

    // Set active menu for sidebar
    request.setAttribute("activeMenu", "courses");
%>
<div class="rms-table-card">
    <table class="rms-table align-middle">
        <thead>
        <tr>
            <th>ID</th>
            <th>Code</th>
            <th>Name</th>
            <th>Semesters</th>
            <th>Students</th>
        </tr>
        </thead>
        <tbody>
        <% if (courses != null && !courses.isEmpty()) {
            for (Course course : courses) { %>
        <tr>
            <td><%= course.getId() %>
            </td>
            <td><%= course.getCode() %>
            </td>
            <td><%= course.getName() %>
            </td>
            <td><%= course.getSemesterCount() %>
            </td>
            <td><%= course.getTotalStudents() %>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="5" class="text-center py-8 text-gray-500">
                No courses found. <a href="${pageContext.request.contextPath}/WEB-INF/admin/courses/new-course.jsp"
                                     class="text-blue-600 hover:underline">Create your first course</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>