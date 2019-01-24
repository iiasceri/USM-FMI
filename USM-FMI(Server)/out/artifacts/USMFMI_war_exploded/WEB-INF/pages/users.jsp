<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/style/style.css" />">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="../resources/js/hello.js"></script>
    <title>Users</title>
</head>
<body>

<h3>All users username:</h3>
<div id="test" class="panel panel-default">
    <div class="panel-body">
        <c:forEach items="${userList}" var="user">
            <p class="hea" style="cursor: pointer" onclick=getUserById(${user.id})>
                    ${user.username}
                    ${user.status}
                <a href="<c:url value="/user/delete-by-id/${user.id}" />">X</a>
            </p>

        </c:forEach>
        <blockquote class="blockquote">
            <p id="showUserInfo" class="mb-0 text-danger"></p>
        </blockquote>
    </div>
</div>
<input type="button" value="JSON example" onclick="getAllUsersAsJson()" class="btn-danger btn-lg"/>
<div id="users"></div>
</body>
</html>
