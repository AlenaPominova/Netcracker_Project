<!DOCTYPE html>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<head>
    <title>Parkings</title>
    <#setting locale="en">
    <#setting number_format="0.######">
    <style>
        form {
            display: inline-block;
        }

        #map {
            position: absolute;
            top: 185px;
            height: 600px;
            width: 100%;
            background-color: grey;
        }

        .block2 {
            width: 380px;
            background: #dddddd;
            float: left;
            position: absolute;
            padding-top: 10px;
        }
    </style>
    <#assign url = springMacroRequestContext.getPathToServlet() >
</head>
<body>
     <@security.authorize access="isAuthenticated()">
        logged in as <@security.authentication property="principal" />
        granted authorities <@security.authentication property="authorities" />
     </@security.authorize><br>
    <@security.authorize access="hasRole('ANONYMOUS')">This text is only visible to an ANONYMOUS</@security.authorize><br>
    <@security.authorize access="hasRole('USER')">This text is only visible to an USER</@security.authorize><br>
    <@security.authorize access="hasRole('OWNER')">This text is only visible to an OWNER</@security.authorize><br>
    <@security.authorize access="hasRole('ADMIN')">This text is only visible to an ADMIN</@security.authorize><br>

    <@security.authorize access="! isAuthenticated()">
        <form method="get" action="${url}/login">
            <input type="submit" value="LOGIN"/>
        </form>
    </@security.authorize>

    <@security.authorize access="isAuthenticated()">
        <form method="get" action="${url}/logout">
            <input type="submit" value="LOGOUT"/>
        </form>
    </@security.authorize>
<br>
<div id="map"></div>
<script>

    function initMap() {
        var options = {
            zoom: 13,
            center: new google.maps.LatLng(51.660702, 39.200206)
        }
        var map = new google.maps.Map(document.getElementById('map'), options);

    <#list parkingsList?keys as key>
        <#assign parkin = parkingsList?api.get(key)>
        var url = '${url}'
        var parking = [];
        parking.name = '${parkin.name}';
        parking.address = '${parkin.values?api.get(303?long)}'
        parking.latitude = '${parkin.values?api.get(301?long)}'
        parking.longitude = '${parkin.values?api.get(302?long)}'
        parking.price = '${parkin.values?api.get(304?long)}'
        parking.open_time = '${(parkin.dateValues?api.get(305?long))?time}'
        parking.close_time = '${parkin.dateValues?api.get(306?long)?time}'
        parking.free_spots_count = '${parkin.values?api.get(307?long)}'
        parking.rating = '${parkin.values?api.get(100?long)?number}'
        parking.status = '${parkin.listValues?api.get(308?long)}'
        parking.owner_id = '${parkin.references?api.get(300?long)}'
        createMarker(parking);
    </#list>

        function createMarker(parking) {
            var marker = new google.maps.Marker({
                position: new google.maps.LatLng(parking.latitude, parking.longitude),
                map: map
            });
            var infoWindow = new google.maps.InfoWindow({
                content: '<h1>' + parking.name + '</h1><br>Адрес: ' + parking.address + '<br>Цена за час: ' + parking.price + '<br>Время работы: ' + parking.open_time + ' - ' + parking.close_time + '<br>Свободных мест: ' + parking.free_spots_count + '<br>Рейтинг: ' + parking.rating
                + '<br><a href="/objects/' + parking.owner_id + '"> Владелец: </a>'


            });

            marker.addListener('click', function () {
                infoWindow.open(map, marker);
            });
        }
    }
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHepL7UX34Cu6Ft9cGn4IzDNp9-rKPaNc&callback=initMap">
</script>
<div class="block2" align="center">
    <#list parkingsList?keys as key>
        <#assign parking = parkingsList?api.get(key)>
        <table border="1" cellspacing="0" bgcolor="#eeeeee">
            <tr>
                <td width="150">Id:</td>
                <td width="190">${parking.id}</td>
            </tr>
            <tr>
                <td>Парковка:</td>
                <td>${parking.name}</td>
            </tr>
            <tr>
                <td>OwnerId:</td>
                <td>${parking.references?api.get(300?long)?string}</td>
            </tr>
            <tr>
                <td>Address:</td>
                <td>${parking.values?api.get(303?long)}</td>
            </tr>
            <tr>
                <td>Latitude:</td>
                <td>${parking.values?api.get(301?long)}</td>
            <tr>
                <td>Longitude:</td>
                <td>${parking.values?api.get(302?long)}</td>
            </tr>
            <tr>
                <td>Price $ / hour:</td>
                <td>${parking.values?api.get(304?long)}</td>
            </tr>
            <tr>
                <td>Free spots count:</td>
                <td>${parking.values?api.get(307?long)}</td>
            </tr>
            <tr>
                <td> Avaliability time:</td>
                <td>${parking.dateValues?api.get(305?long)?time} - ${parking.dateValues?api.get(306?long)?time}</td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>${parking.listValues?api.get(308?long)}</td>
            </tr>
            <tr>
                <td>Rating:</td>
                <td>${parking.values?api.get(100?long)?number}</td>
            </tr>
            <tr>
                <td colspan="2" height="40" align="center" bgcolor="eeeeee">
                    <form method="get" action="${url}/parkings/${parking.id}">
                        <input type="submit" value="View"/>
                    </form>
                </td>
            </tr>
        </table>
        <br>
    </#list>
</div>
</body>
</html>