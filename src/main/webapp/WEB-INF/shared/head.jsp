<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- This single line fixes the root-context trailing-slash bug once and for all -->
<base href="${pageContext.request.contextPath}/">

<!-- Your global CSS (Bootstrap + your own if any) -->
<%--<link rel="stylesheet" href="css/bootstrap.min.css">--%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">

<!-- optional -->

<!-- Global JS at the top if needed -->

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" defer></script>
<style>
    a {
        text-decoration: none;
    }

    * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
    }

    dl, ol, ul {
        margin: 0;
        padding: 0;
    }

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