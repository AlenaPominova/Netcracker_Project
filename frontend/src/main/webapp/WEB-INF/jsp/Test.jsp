<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ page import="com.parking.server.objects.Pojo" %>
<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<html>
<head>
    <title>Test</title>
    <script src="http://yastatic.net/jquery/2.1.4/jquery.min.js"></script>
</head>

<body>

<jsp:useBean id="Pojo" class="com.parking.server.objects.Pojo" scope="request"/>
<jsp:getProperty name="Pojo" property="name"/>
${list[0].values[302]}

<script>var foo = '${list[0].values[302]}';</script>
<!--<table id="parkings">
    <tr><td>Id</td><td>Name</td><td>Type_id</td><tr>
</table>-->
<script type="text/javascript">

    var ex=123;
    document.write(ex);
    document.write('   ');
    document.write('${list[0].values[302]}');
    document.write('   ');
    document.write('${size}');

    <c:forEach items="${list}" var="temp">
    <c:out value="${temp.values[302]}"></c:out>

    var test = "${temp.values[302]}";

    document.write("     ");
    </c:forEach>




    for (var i = 0; i < '${size}'; i++) {
        var temp = ['${list[i].values[302]}', '${list[i].values[303]}'];
        var marker25 = L.marker(temp).addTo(map);
    }





    //$(function(){
        /*$.getJSON('http://localhost:8082/parking/cache_json').done(function(data) { //, function(data) {
            data = JSON.parse(data);
            for(var i=0;i<data.parkings.length;i++){
                document.write(ex);
                $('#parkings').append('<tr><td>' + data.parkings[i].id + '</td><td>' + data.parkings[i].name +
                    '</td><td>' + data.parkings[i].type_id + '</td><tr>');
            }
        });*/
    //});


    /*$.ajax({
        type: 'GET',
        url: 'http://localhost:8082/parking/cache_json',
        contentType: 'text/plain',
        xhrFields: { withCredentials: false },
        headers: {},
        success: function(data) {
            for (var i = 0; i < data.parkings.length; i++){
                var temp = data.parkings[i].id;
                document.write(temp);
            }
        },
        error: function() {
            alert('Error');
        }
    });*/

</script>


</body>

</html>















<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <title>Parking</title>


</head>
<body>

<jsp:include page="/myservlet.jsp" />




<script type="text/javascript">

    var test = 0;

    <c:forEach items="${list}" var="temp">

    test = "${temp.values[302]}";

    document.write("     ");
    </c:forEach>

    document.write(test);

    for (var i = 0; i < '${size}'; i++) {

        var temp = ['${list[i].values[302]}', '${list[i].values[303]}'];

        //var marker25 = L.marker(temp).addTo(map);
    }

</script>

</body>
</html>
