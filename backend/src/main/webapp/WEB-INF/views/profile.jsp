<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>User profile</title>
    <%@ page isELIgnored="false" %>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script language="javascript" type="text/javascript">
        $(function () {
            $('.button_edit').click(function () {

                $('#info td:nth-child(2)').each(function () {
                    var value = $(this).html();
                    var code = '<input type="text" id="edit" value="'+ value +'" />';
                    $(this).empty().append(code);
                });
                $(".button_edit").attr('disabled', true);
            });
            $('.button_save').click(function () {
                var key = [];
                var value = [];
                var json = {};
                var i = 0;
                $(".button_edit").attr('disabled', false);
                $('#info td:nth-child(2)').each(function () {
                    var val = $(this).html();
                    if (val.indexOf('value="') != -1) {
                        value[i] = $('#edit').val();
                        $(this).empty().append(value[i]);
                    } else{
                        value[i] = val;
                    }
                    i++;
                });
                i = 0;
                $('#info td:nth-child(1)').each(function () {
                    key[i] = $(this).html();
                    i++;
                });
                for (var j = 0; j < key.length; j++){
                    var k = key[j];
                    var v = value[j];
                    json[k] = v;
                }
                alert(JSON.stringify(json));
            });
            $('.button_delete').click(function () {
                var person = {
                    id: parseInt($("#info td:nth-child(2)").html())
                }
                $.ajax({
                    url:'http://localhost:8081/delete',
                    type:'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(person)
                });
            });
        });
    </script>
</head>
<body>
    <h1>Profile page</h1>
    <button class="button_save" onclick="" >Save</button>
    <button class="button_edit" >Edit</button>
    <button class="button_delete" >Delete</button>
    <table id="info" border="1">
        <tr>
            <th>Имя атрибута</th>
            <th>Значение</th>
        </tr>
        <tr><td id="indeteficator">id</td><td>${id}</td></tr>
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
