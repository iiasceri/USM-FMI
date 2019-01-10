<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adauga grupe</title>
    <link rel="stylesheet" href="../resources/style/style.css">
</head>
<body>

<div align="center">
    <div style="width: 300px; height: 500px;">
        <h1>Salut
            <security:authorize access="isAuthenticated()">
                <security:authentication property="principal.username" />
            </security:authorize>!
        </h1>
        <form:form method="POST" action="/add-group" modelAttribute="group" cssClass="frm">
            <div class="container" align="left">
                <label>Numele grupei</label>
                <input type="text" name="name" required="required" class="inp"/>

                <label>Anul [1-3]</label>
                <input type="number" name="year" required="required" class="inp"/>

                <button type="submit" class="btn">Creaza Grupa</button>
            </div>
        </form:form>

        <div id="test" class="panel panel-default">
            <div class="panel-body">
                <c:forEach items="${groupList}" var="group">
                    <p class="hea" style="cursor: pointer"> ${group.name}
                        <a href="schedule-by-name/${group.name}">Orar</a> |
                        <a href="exam-schedule-by-name/${group.name}">Orar Sesiune</a>
                    </p>
                </c:forEach>
            </div>
        </div>

        <div align="right">
             <a href="<c:url value="/logout" />">Iesire</a>
        </div>
    </div>
</div>
</body>
</html>
