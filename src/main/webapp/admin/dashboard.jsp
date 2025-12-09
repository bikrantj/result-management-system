<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Tell sidebar which menu is active
    request.setAttribute("activeMenu", "dashboard");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <%@ include file="/shared/head.jsp" %>

</head>

<body class="m-0 p-0">

<div style="display:flex; min-height:100vh;">

    <!-- Sidebar -->
    <jsp:include page="/shared/sidebar-admin.jsp"/>

    <!-- Main Content -->
    <div style="flex:1; background:#f5f5f5;" class="main-content-area">
        <jsp:include page="dashboard_content.jsp"/>
    </div>

</div>

</body>
</html>
