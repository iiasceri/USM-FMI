<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../resources/style/style.css">
</head>
<body>
<div align="center">
    <div style="width: 300px; height: 500px;">
        <form:form method="POST" action="${pageContext.request.contextPath}/register" modelAttribute="user" cssClass="frm">
            <div class="imgcontainer">
                <img src="../resources/pictures/usm_bottle_green_400x400.png" alt="Avatar" class="avatar">
            </div>
            <h3 align="center" style="color:#009b76;">Completati Campurile!</h3>

            <div class="container" align="left">
                <label>Nume Utilizator</label>
                <input type="text" name="username" required="required" class="inp"/>

                <label>Mail</label>
                <input type="text" name="mail" required="required" class="inp"/>

                <label>Nume Prenume</label>
                <input type="text" name="familyname" required="required" class="inp"/>

                <label>Parola</label>
                <input type="password" name="password" required="required" class="inp"/>

                <label>Genul </label>
                <input type="radio" name="gender" value="FEMALE"> F
                <input type="radio" name="gender" value="MALE"> M

                <label>| Grupa</label>
                    <select name="groupName" id="groupName" >
                        <c:forEach var="groups" items="${groupList}">
                            <option value="${groups.name}">${groups.name}</option>
                        </c:forEach>
                    </select>
                <label>SubGrupa </label>
                <input type="radio" name="subGroup" value="I" id="subGroup"> I (s)
                <input type="radio" name="subGroup" value="II" id="subGroup"> II (d)
                <button type="submit" class="btn">Inregistreaza-ma</button>
            </div>


        </form:form>
        <div align="right">
             <a href="<c:url value="/logout" />">Iesire</a>
        </div>
    </div>
</div>
</body>
</html>