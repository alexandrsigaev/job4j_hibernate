<%--
  Created by IntelliJ IDEA.
  User: aleksandrsigaev
  Date: 2019-05-21
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SignIn</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/floating-labels/">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.0.js"
            integrity="sha256-DYZMCC8HTC+QDr5QNaIcfR7VSPtcISykd+6eSmBW5qo="
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm"></div>
        <div class="col-sm">

            <form class="form-signin" method="post" action="${pageContext.servletContext.contextPath}/signIn">
                <div class="text-center mb-4" style="padding-top: 300px"></div>
                <c:if test="${error != ''}">
                    <div class="alert alert-danger" role="alert">
                        <c:out value="${error}"/>
                    </div>
                </c:if>
                <div class="form-label-group text-center mb-3">
                    <label for="login">Login</label>
                    <input type="text" id="login" name="login" class="form-control" placeholder="Login" required autofocus>
                </div>

                <div class="form-label-group text-center mb-3">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
                </div>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>

            <form method="get" action="${pageContext.servletContext.contextPath}/signIn">
                <label for="password">Registration:</label>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign Up</button>
            </form>

        </div>
        <div class="col-sm">
        </div>
    </div>
</div>
</body>
</html>
