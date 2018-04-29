<!DOCTYPE html>
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
        .menu {
            width: 380px;
            float: left;
            position: absolute;
            top: 10px;
        }
        .block2 {
            width: 380px;
            background: #dddddd;
            float: left;
            position: absolute;
            top: 190px;
            padding-top: 10px;
        }
    </style>
    <#assign url = springMacroRequestContext.getPathToServlet() >
</head>
<body>


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

        function createMarker(parking){
            var marker = new google.maps.Marker({
                position: new google.maps.LatLng(parking.latitude, parking.longitude),
                map: map
            });
            var infoWindow = new google.maps.InfoWindow({
                content: '<h1>' + parking.name + '</h1><br>Адрес: ' + parking.address + '<br>Цена за час: ' + parking.price + '<br>Время работы: ' + parking.open_time + ' - ' + parking.close_time + '<br>Свободных мест: ' + parking.free_spots_count + '<br>Рейтинг: ' + parking.rating
                + '<br><a href="/objects/' + parking.owner_id + '"> Владелец: </a>'


            });

            marker.addListener('click', function(){
                infoWindow.open(map, marker);
            });
        }
    }
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHepL7UX34Cu6Ft9cGn4IzDNp9-rKPaNc&callback=initMap">
</script>
+ '<input type="submit" formaction="/restapi/evac/send-request" value="Save Cache"/>'
<#--+ '<br><a href="' + url'">Владелец:</a>'-->
<#--+ '<br><a href="${url}/objects/"' + parking.owner_id>Страница владельца'</a>-->
<div class="menu" align="center">
    <table width="345" border="1" cellspacing="0" bgcolor="#eeeeee">
        <tr height="45">
            <th colspan="2">MENU ${url}</th>
        </tr>
        <tr height="40">
            <td align="center" bgcolor="eeeeee">
                <form method="get" action="${url}/read-cache/"">
                <input type="submit" value="Get Cache"/>
                </form>
            </td>
            <td align="center">
                <form method="get" action="${url}/save-cache/"
                ">
                <input type="submit" value="Save Cache"/>
                </form>
            </td>
        </tr>
        <tr height="40">
            <td align="center" bgcolor="eeeeee">
                <form method="get" action="${url}/profiles/create/">
                    <input type="submit" value="Create User"/>
                </form>
            </td>
            <td align="center" bgcolor="eeeeee">
                <form method="get" action="${url}/profiles/">
                    <input type="submit" value="View Users"/>
                </form>
            </td>
        <tr height="40">
            <td align="center" bgcolor="eeeeee">
                <form method="get" action="${url}/parkings/create/">
                    <input type="submit" value="Create Parking"/>
                </form>
            </td>
            <td align="center" bgcolor="eeeeee">
                <form method="get" action="${url}/parkings/">
                    <input type="submit" value="View Parkings"/>
                </form>
            </td>
        </tr>
    </table>
</div>

<div class="block2" align="center">
    <#list parkingsList?keys as key>
        <#assign parking = parkingsList?api.get(key)>
            <table border="1" cellspacing="0" bgcolor="#eeeeee">
                <tr>
                    <td width="200">Id:</td>
                    <td width="250">${parking.id}</td>
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
                            <input type="submit" value="View" />
                        </form>
                    </td>
                </tr>
            </table>
        <br>
    </#list>
</div>
</body>
</html>