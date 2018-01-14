<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form method="POST" commandName="user" action="check-user">
    <fieldset>
        <p><form:label path="name">Name:</form:label></p>
        <form:input path="name"></form:input>
        <form:label path="password">Password:</form:label>
        <form:password path="password"></form:password>
    </fieldset>

    <footer>
        <button type="submit"> Submit </button>
    </footer>
</form:form>
</html>