<%@ page import="com.riya.rms.models.Exam" %>
<%@ page import="com.riya.rms.models.StudentSubjectMarkDTO" %>
<%@ page import="java.util.List" %>
<%
    Exam exam = (Exam) request.getAttribute("exam");
    List<StudentSubjectMarkDTO> marks = (List<StudentSubjectMarkDTO>) request.getAttribute("marks");
    request.setAttribute("activeMenu", "dashboard");
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= exam.getName() %> - Results</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>
</head>

<body class="bg-gray-50 font-sans antialiased">

<div class="flex min-h-screen">
    <jsp:include page="/WEB-INF/shared/sidebar-student.jsp"/>

    <main class="flex-1 p-6">
        <div class="mb-8">
            <h1 class="text-3xl font-bold text-gray-900 mb-2">
                <%= exam.getName() %> Results
            </h1>
            <p class="text-gray-600">
                <%= exam.getCourseName() %> - <%= exam.getSemesterName() %>
            </p>
        </div>

        <% if (exam.isPublished()) { %>

        <div class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
            <table class="w-full">
                <thead class="bg-gray-50">
                <tr>
                    <th class="px-6 py-4 text-left text-sm font-medium text-gray-700">Subject</th>
                    <th class="px-6 py-4 text-center text-sm font-medium text-gray-700">Full Marks</th>
                    <th class="px-6 py-4 text-center text-sm font-medium text-gray-700">Obtained</th>
                    <th class="px-6 py-4 text-center text-sm font-medium text-gray-700">Percentage</th>
                </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                <% for (StudentSubjectMarkDTO m : marks) { %>
                <tr class="hover:bg-gray-50">
                    <td class="px-6 py-4 text-sm text-gray-900"><%= m.getSubjectName() %>
                    </td>
                    <td class="px-6 py-4 text-center text-sm text-gray-600"><%= m.getFullMarks() %>
                    </td>
                    <td class="px-6 py-4 text-center text-sm font-medium text-gray-900">
                        <%= m.getMarksObtained() != null ? String.format("%.2f", m.getMarksObtained()) : "N/A" %>
                    </td>
                    <td class="px-6 py-4 text-center text-sm font-medium text-gray-700">
                        <%= m.getMarksObtained() != null ?
                                String.format("%.1f%%", (m.getMarksObtained() / m.getFullMarks()) * 100) : "N/A" %>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>

        <% } else { %>
        <div class="text-center py-16 bg-white rounded-2xl shadow-sm border border-gray-100">
            <p class="text-xl text-gray-600">Results for this exam have not been published yet.</p>
            <p class="mt-2 text-gray-500">Please check back later.</p>
        </div>
        <% } %>

    </main>
</div>

</body>
</html>