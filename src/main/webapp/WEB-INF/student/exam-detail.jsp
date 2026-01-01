<%@ page import="com.riya.rms.models.StudentSubjectMarkDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.Exam" %>
<%@ page import="com.riya.rms.models.User" %>

<%
    Exam exam = (Exam) request.getAttribute("exam");
    User student = (User) request.getAttribute("student");
    List<StudentSubjectMarkDTO> marks =
            (List<StudentSubjectMarkDTO>) request.getAttribute("marks");

    double totalFull = (double) request.getAttribute("totalFull");
    double totalObtained = (double) request.getAttribute("totalObtained");
    boolean isPassed = (boolean) request.getAttribute("isPassed");
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= exam.getName() %> - Mark Sheet</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>
    <script src="<%= request.getContextPath() %>/js/html2pdf.bundle.min.js"></script>
    <script>
        function downloadPDF() {
            const element = document.getElementById("marksheet");

            const options = {
                margin: 0.5,
                filename: 'Result_<%= exam.getName().replaceAll(" ", "_") %>.pdf',
                image: {type: 'jpeg', quality: 0.98},
                html2canvas: {
                    scale: 2,
                    useCORS: true
                },
                jsPDF: {
                    unit: 'in',
                    format: 'a4',
                    orientation: 'portrait'
                }
            };

            html2pdf().set(options).from(element).save();
        }
    </script>
</head>

<body class="bg-gray-50 font-serif p-8">
<div class="flex flex-col gap-6 items-center">
    <button onclick="downloadPDF()"
            class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
        Download Report (PDF)
    </button>

    <div id="marksheet"
         class="max-w-5xl mx-auto bg-white p-10 shadow border">

        <h2 class="text-center text-xl font-bold mb-2">
            Kantipur City College
        </h2>
        <p class="text-center text-sm mb-6">
            <%= exam.getCourseName() %> - Semester <%= exam.getSemesterName() %>
        </p>

        <h3 class="text-center text-lg font-semibold mb-6">
            <%= exam.getName() %> - Grade / Mark Sheet
        </h3>

        <p class="text-center mb-6 text-lg font-semibold
          <%= isPassed ? "text-green-700" : "text-red-700" %>">
            <%= isPassed ? "Congratulations! You have Passed." : "Result: Failed" %>
        </p>

        <p>
            Student Name: <%= student.getName() %>
        </p>

        <table class="w-full border-collapse border">
            <thead>
            <tr class="bg-gray-100">
                <th class="border px-3 py-2">S.N</th>
                <th class="border px-3 py-2 text-left">Subject</th>
                <th class="border px-3 py-2">Full Marks</th>
                <th class="border px-3 py-2">Pass Marks</th>
                <th class="border px-3 py-2">Marks Obtained</th>
            </tr>
            </thead>
            <tbody>
            <% int i = 1;
                for (StudentSubjectMarkDTO m : marks) { %>
            <tr>
                <td class="border px-3 py-2 text-center"><%= i++ %>
                </td>
                <td class="border px-3 py-2"><%= m.getSubjectName() %>
                </td>
                <td class="border px-3 py-2 text-center"><%= m.getFullMarks() %>
                </td>
                <td class="border px-3 py-2 text-center">
                    <%= m.getPassMarks() != null ? m.getPassMarks() : "—" %>
                </td>

                <td class="border px-3 py-2 text-center
    <%= (m.getMarksObtained() != null
          && m.getPassMarks() != null
          && m.getMarksObtained() < m.getPassMarks())
        ? "text-red-700 font-semibold"
        : "text-gray-800" %>">

                    <%= m.getMarksObtained() != null ? m.getMarksObtained() : "—" %>
                </td>
            </tr>
            <% } %>
            </tbody>

            <tfoot>
            <tr class="font-bold bg-gray-50">
                <td colspan="2" class="border px-3 py-2 text-right">Grand Total</td>
                <td class="border px-3 py-2 text-center"><%= totalFull %>
                </td>
                <td class="border px-3 py-2"></td>

                <td class="border px-3 py-2 text-center"><%= totalObtained %>
                </td>
            </tr>
            </tfoot>
        </table>

    </div>
</div>
</body>
</html>
