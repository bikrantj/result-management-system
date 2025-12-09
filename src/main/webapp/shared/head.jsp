<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- This single line fixes the root-context trailing-slash bug once and for all -->
<base href="${pageContext.request.contextPath}/">

<!-- Your global CSS (Bootstrap + your own if any) -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/table.css">

<!-- optional -->

<!-- Global JS at the top if needed -->
<script src="js/bootstrap.min.js" defer></script>
<style>
    .sidebar {
        width: 250px;
        position: fixed;
        top: 0;
        left: 0;
        height: 100%;
    }

    .main-content-area {
        padding: 20px;
    }
</style>