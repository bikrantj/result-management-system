<%
    // Tell sidebar which menu is active
    int totalStudents = (int) request.getAttribute("totalStudents");
    int totalTeachers = (int) request.getAttribute("totalTeachers");
    int totalCourses = (int) request.getAttribute("totalCourses");
    int totalSubjects = (int) request.getAttribute("totalSubjects");
    request.setAttribute("activeMenu", "dashboard");
%>
<div class="p-4">
    <h2>Dashboard Overview</h2>
    <p>Welcome to the admin dashboard.</p>

    <!-- Example dashboard cards -->
    <div class="row mt-4">
        <div class="col-md-3">
            <div class="card shadow-sm p-3">
                <h5>Total Students</h5>
                <p><%=totalStudents%>
                </p>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card shadow-sm p-3">
                <h5>Total Teachers</h5>
                <p><%=totalTeachers%>
                </p>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card shadow-sm p-3">
                <h5>Total Courses</h5>
                <p><%=totalCourses%>
                </p>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card shadow-sm p-3">
                <h5>Total Subjects</h5>
                <p><%=totalSubjects%>
                </p>
            </div>
        </div>
    </div>
</div>
