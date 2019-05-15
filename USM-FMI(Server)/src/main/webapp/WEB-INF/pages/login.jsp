<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*"  %>
<html>
<head>
    <title>Login page</title>
    <%--<link rel="stylesheet" href="http://localhost:8080/USMFMI/resources/style/style.css">--%>
    <link href="<c:url value="/resources/style/style.css" />" rel="stylesheet">
    <%--<link rel="stylesheet" href="/resources/style/style.css">--%>
    <meta charset="UTF-8">
    <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.1.3/flatly/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.contextMenu.min.css">
</head>
<body>
<div class="frm-container">
    <div class="frm-wrapper">
        <form:form method="POST" action="${pageContext.servletContext.contextPath}/login" modelAttribute="user" cssClass="frm">
            <div class="imgcontainer">
                <img src="<c:url value="/resources/pictures/usm_bottle_green_400x400.png" />" alt="Avatar" class="avatar">
            </div>
            <h3 align="center" style="color:#009b76;">Autentificati-va</h3>

            <div class="container" align="left">
                <label>Nume Utilizator</label>
                <input type="text" name="username" required="required" class="inp"/>

                <label>Parola</label>
                <input type="password" name="password" required="required" class="inp"/>

                <p style="color:red">${error}</p>
                <button type="submit" class="btn">Autentificare</button>

                <div align="center">
                    <a href="${pageContext.servletContext.contextPath}/register">Creaza cont!</a>
                </div>
            </div>
        </form:form>

        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.contextMenu.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.ui.position.js"></script>
    </div>
</div>
</body>
</html>
