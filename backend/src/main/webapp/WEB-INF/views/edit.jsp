<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit profile</title>
</head>
<body>
    <h1>Profile page</h1>
    <button class="button_save" >Save</button>
    <button class="button_edit" >Edit</button>
    <button class="button_delete" >Delete</button>
    <table border="1">
        <tr>
            <th>Имя атрибута</th>
            <th>Значение</th>
        </tr>
        <tr><td>id</td><td>${id}</td></tr>
        <tr><td>name</td><td>${name}</td></tr>
        <tr><td>type_id</td><td>${type_id}</td></tr>
        <c:forEach var="entry" items="${data}">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
