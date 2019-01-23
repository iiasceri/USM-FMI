<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adauga grupe</title>
    <link rel="stylesheet" href="../resources/style/style.css">
    <meta charset="UTF-8">
    <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.1.3/flatly/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.contextMenu.min.css">
    <style>
        body { background-color: #e9e9e9; }
        .container { margin: 1px auto; }
    </style>
</head>
<body>

<div align="center">
    <div style="width: 300px; height: 500px;">
        <h1>Salut
            <security:authorize access="isAuthenticated()">
                <security:authentication property="principal.username" />
            </security:authorize>!
        </h1>
        <form:form method="POST" action="${pageContext.servletContext.contextPath}/add-group" modelAttribute="group" cssClass="frm">
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

        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.contextMenu.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.ui.position.js"></script>

        <script src="../resources/js/jsoneditor.js"></script>

        <script>
            $(document).ready(function () {
                jsonEditorInit('table_container', 'Textarea1', 'result_container', 'json_to_table_btn', 'table_to_json_btn');
            });
        </script>
    </div>
</div>
</body>
</html>
