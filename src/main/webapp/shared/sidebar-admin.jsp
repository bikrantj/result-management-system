<%
    String activeMenu = (String) request.getAttribute("activeMenu");
    String current = activeMenu == null ? "" : activeMenu;
%>

<div class="bg-dark text-white vh-100 p-3"
     style="width:250px; min-height:100vh; position:sticky; top:0;">

    <h4 class="text-center mb-4 text-warning">ADMIN PANEL</h4>
   
    <hr class="border-secondary">

    <ul class="nav nav-pills flex-column">
        <li class="nav-item mb-2">
            <a href="${pageContext.request.contextPath}/admin/dashboard.jsp"
               class="nav-link text-white <%= "dashboard".equals(current) ? "active bg-primary" : "" %>">
                Dashboard
            </a>
        </li>

        <li class="nav-item mb-2">
            <a href="${pageContext.request.contextPath}/admin/courses/"
               class="nav-link text-white <%= "courses".equals(current) ? "active bg-primary" : "" %>">
                Courses
            </a>
        </li>

        <li class="nav-item mb-2">
            <a href="${pageContext.request.contextPath}/admin/students/"
               class="nav-link text-white <%= "students".equals(current) ? "active bg-primary" : "" %>">
                Students
            </a>
        </li>

        <li class="nav-item mb-2">
            <a href="${pageContext.request.contextPath}/admin/teachers/"
               class="nav-link text-white <%= "teachers".equals(current) ? "active bg-primary" : "" %>">
                Teachers
            </a>
        </li>
    </ul>
</div>
