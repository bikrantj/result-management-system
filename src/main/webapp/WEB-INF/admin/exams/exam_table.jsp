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
        <th>Full Marks</th>
    </tr>
    </thead>

    <tbody>
    <% for (Exam e : exams) { %>
    <tr>
        <td><%= e.getName() %>
        </td>
        <td><%= e.getCourseName() %>
        </td>
        <td><%= e.getSemesterName() %>
        </td>
        <td><%= e.getFullMarks() %>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
