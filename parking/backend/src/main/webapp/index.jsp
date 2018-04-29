<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <h1>WELLCOME Backend</h1>
    <h3>Management Console</h3>
    <% request.setAttribute("url", request.getContextPath()); %>
</head>
<body>
    <table border="1" cellspacing="0" bgcolor="#eeeeee" cellpadding="10">
        <tr><td>/management/{objectId} - for the object with specified Id (with edit/delete)</td><td><a href="${uri}/management/4">/management/4</a></td></tr>
        <tr><td>/restapi/{objectId} - for json representation of object</td><td><a href="${uri}/restapi/4">/restapi/4</a></td></tr>
        <tr><td>/restapi/users - list of users in json format</td><td><a href="${uri}/restapi/users">/restapi/users</a></td></tr>
        <tr><td>/restapi/parkings - list of parkings in json format</td><td><a href="${uri}/restapi/parkings">/restapi/parkings</a> </td></tr>
        <tr><td>/restapi/attributes - list of attributes in json format</td><td><a href="${uri}/restapi/attributes">/restapi/attributes</a> </td></tr>
    </table>
</body>
</html>
