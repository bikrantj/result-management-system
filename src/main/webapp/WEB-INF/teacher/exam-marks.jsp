<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.Exam" %>
<%@ page import="com.riya.rms.models.StudentMarkDTO" %>

<%
    Exam exam = (Exam) request.getAttribute("exam");
    List<StudentMarkDTO> students =
            (List<StudentMarkDTO>) request.getAttribute("students");
    request.setAttribute("activeMenu", "dashboard");

%>

<!DOCTYPE html>
<html>
<head>
    <title>Enter Marks</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>
</head>

<body class="bg-gray-50">

<div class="flex min-h-screen">
    <jsp:include page="/WEB-INF/shared/sidebar-teacher.jsp"/>

    <main class="flex-1 p-6">
        <h1 class="text-2xl font-bold mb-6">
            <%= exam.getName() %> - <%= exam.getSubjectName() %> <span
                class=" text-gray-600">(<%= exam.getCourseName() %> -
            <%= exam.getSemesterName() %>)</span>
        </h1>

        <% if (request.getAttribute("success") != null) { %>
        <div class="mt-4 p-4 bg-green-100 text-green-800 rounded">
            <%= request.getAttribute("success") %>
        </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
        <div class="mt-4 p-4 bg-red-100 text-red-800 rounded">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/teacher/exams/<%= exam.getId() %>">
            <table class="w-full bg-white rounded-xl shadow">
                <thead class="bg-gray-100">
                <tr>
                    <th class="p-3 text-left">Student ID</th>
                    <th class="p-3 text-left">Name</th>
                    <th class="p-3 text-left">
                        Marks (Out of <%= exam.getFullMarks() %>)
                    </th>
                </tr>
                </thead>

                <tbody>
                <% for (StudentMarkDTO s : students) { %>
                <tr class="border-t">
                    <td class="p-3"><%= s.getStudentId() %>
                    </td>
                    <td class="p-3"><%= s.getStudentName() %>
                    </td>
                    <td class="p-3">
                        <input type="number"
                               name="marks_<%= s.getStudentId() %>"
                               max="<%= exam.getFullMarks() %>"
                               value="<%= s.getMarks() > 0 ? s.getMarks() : "0" %>"
                               min="0"
                               class="w-24 border rounded px-2 py-1"/>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>

            <button class="mt-6 px-6 py-2 bg-blue-600 text-white rounded-lg">
                Save Marks
            </button>
        </form>
    </main>
</div>

</body>
</html>
