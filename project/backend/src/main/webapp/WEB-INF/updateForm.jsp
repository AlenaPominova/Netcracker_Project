<%--
  Created by IntelliJ IDEA.
  User: Алена
  Date: 16.02.2018
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<body>
<div class="container">

    <spring:url value="/object" var="userActionUrl" />

    <form:form class="form-horizontal" method="get" modelAttribute="userForm" action="${userActionUrl}">
        <table>
            <tr>
                <spring:bind path="name">
                    <td>Name</td>
                    <td><form:input path="name" type="text" id="name"/></td>
                </spring:bind>
            </tr>
            <tr>
                <spring:bind path="id">
                    <td>id</td>
                    <td><form:input path="id" id="id"/></td>
                </spring:bind>
            </tr>
            <tr>
                <spring:bind path="typeId">
                    <td>typeID</td>
                    <td><form:input path="typeId" id="typeId" /></td>
                </spring:bind>
            </tr>

            <c:forEach items="${userForm.values}" var="val" varStatus="pStatus">
                <tr>
                    <c:set var="v" value="${pStatus.current.key}" />
                    <td>${pStatus.current.key}</td>
                    <td>
                        <form:input path="values[${v}]" name="${v}" value="${pStatus.current.value}"/>
                    </td>
                </tr>
            </c:forEach>

            <c:forEach items="${userForm.date}" var="val" varStatus="pStatus">
                <tr>
                    <c:set var="v" value="${pStatus.current.key}" />
                    <td>${pStatus.current.key}</td>
                    <td>
                        <form:input path="date[${v}]" name="${v}" value="${pStatus.current.value}"/>
                    </td>
                </tr>
            </c:forEach>

            <c:forEach items="${userForm.reference}" var="val" varStatus="pStatus">
                <tr>
                    <c:set var="v" value="${pStatus.current.key}" />
                    <td>${pStatus.current.key}</td>
                    <td>
                        <form:input path="reference[${v}]" name="${v}" value="${pStatus.current.value}"/>
                    </td>
                </tr>
            </c:forEach>

        </table>

        <button type="submit" class="btn-lg btn-primary pull-right">Update</button>
    </form:form>

</div>

</body>
</html>
