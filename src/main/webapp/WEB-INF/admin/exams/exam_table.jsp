<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.Exam" %>

<%
    List<Exam> exams = (List<Exam>) request.getAttribute("exams");
%>

<table class="rms-table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Course</th>
        <th>Semester</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>

    <tbody>
    <% if (exams != null && !exams.isEmpty()) {
        for (Exam e : exams) { %>
    <tr>
        <td><%= e.getName() %>
        </td>
        <td><%= e.getCourseName() %>
        </td>
        <td><%= e.getSemesterName() %>
        </td>
        <!-- Status Chip -->
        <td>
            <span class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium
                         <%= e.isPublished() ? "bg-green-100 text-green-800" : "bg-orange-100 text-orange-800" %>">
                <%= e.isPublished() ? "Published" : "Not Published" %>
            </span>
        </td>

        <td>
            <% if (!e.isPublished()) { %>
            <form method="post" action="${pageContext.request.contextPath}/admin/exam/publish/<%= e.getId() %>"
                  class="inline"
                  onsubmit="return confirm('Are you sure you want to publish this exam? This action cannot be undone.');">
                <button type="submit"
                        class="px-4 py-2 bg-blue-600 text-white text-sm font-medium rounded hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 transition">
                    Publish
                </button>
            </form>
            <% } else { %>
            <span class="text-gray-500 text-sm">-</span>
            <% } %>
        </td>
    </tr>
    <%
        }
    } else { %>
    <tr>
        <td colspan="6" class="text-center py-8 text-gray-500">
            No exams found.
        </td>
    </tr>
    <% } %>
    </tbody>
</table>