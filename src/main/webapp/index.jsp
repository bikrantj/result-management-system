<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <%@ include file="/WEB-INF/shared/head.jsp" %>
</head>

<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">

            <div class="card shadow">
                <div class="card-body">

                    <h4 class="text-center mb-3">Login</h4>

                    <form action="${pageContext.request.contextPath}/login" method="post">

                        <div class="mb-3">
                            <label class="form-label">Username</label>
                            <input type="text" value="admin" required name="username" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Password</label>
                            <input type="password" value="admin123" required name="password" class="form-control">
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Login</button>
                        <p class="text-danger mt-2">
                            ${error}
                        </p>

                    </form>

                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>
