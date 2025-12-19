<%@ page import="java.util.List" %>
<%@ page import="com.riya.rms.models.Subject" %>

<%
    List<Subject> subjects =
            (List<Subject>) request.getAttribute("subjects");
%>

<% if (subjects != null) { %>
<div class="modal fade show" id="teacherSubjectsModal"
     style="display:block;" tabindex="-1">

    <div class="modal-dialog modal-xl modal-dialog-centered">
        <div class="modal-content rounded-xl shadow-xl">

            <div class="modal-header bg-indigo-600 text-white">
                <h5 class="modal-title">
                    Subjects taught by
                    <%= ((com.riya.rms.models.User)
                            request.getAttribute("selectedTeacher")).getName() %>
                </h5>
                <a href="${pageContext.request.contextPath}/admin/teachers/"
                   class="btn-close btn-close-white"></a>
            </div>

            <div class="modal-body p-4">
                <table class="rms-table w-full">
                    <thead>
                    <tr>
                        <th>Subject Code</th>
                        <th>Subject Name</th>
                        <th>Semester</th>
                        <th>Course</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (subjects.isEmpty()) { %>
                    <tr>
                        <td colspan="4"
                            class="text-center text-gray-500 py-6">
                            No subjects assigned
                        </td>
                    </tr>
                    <% } else {
                        for (Subject s : subjects) { %>
                    <tr>
                        <td><%= s.getCode() %>
                        </td>
                        <td><%= s.getName() %>
                        </td>
                        <td><%= s.getSemesterName() %>
                        </td>
                        <td><%= s.getCourseName() %>
                        </td>
                    </tr>
                    <% }
                    } %>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

<!-- Backdrop -->
<div class="modal-backdrop fade show"></div>
<% } %>
