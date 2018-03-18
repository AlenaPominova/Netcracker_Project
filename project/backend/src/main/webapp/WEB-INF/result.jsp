<%--
  Created by IntelliJ IDEA.
  User: Алена
  Date: 16.02.2018
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Spring MVC Form Handling</title>
</head>

<body>
<h2>Object Information</h2>
<table>
    <tr>
        <form:form method = "GET" action = "/update/${id}">
            <td>
                <button class="btn btn-primary">Edit</button>
            </td>
        </form:form>

    </tr>

    <tr>
        <td>ID</td>
        <td>${id}</td>
    </tr>
    <tr>
        <td>Name</td>
        <td>${name}</td>
    </tr>
    <tr>
        <td>Type id</td>
        <td>${typeId}</td>
    </tr>
    <c:forEach items="${values}" var="values">
        <tr>
            <td>${values.key}</td>
            <td>${values.value}</td>
        </tr>
    </c:forEach>
    <c:forEach items="${date}" var="date">
        <tr>
            <td>${date.key}</td>
            <td>${date.value}</td>
        </tr>
    </c:forEach>
    <c:forEach items="${references}" var="references">
        <tr>
            <td>${references.key}</td>
            <td>${references.value}</td>
        </tr>
    </c:forEach>
</table>
</body>

</html>
