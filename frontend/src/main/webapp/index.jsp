<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>

<head>
    <title>Parking</title>

    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAvbf2afFP1guxom1v2xMIZTb57sVINRMg&callback=initMap">
    </script>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <script src="http://yastatic.net/jquery/2.1.4/jquery.min.js"></script>

    <style>
        <%@include file="/WEB-INF/css/index.css"%>
    </style>

    <script type="text/javascript">
        <%@include file="/WEB-INF/js/index.js"%>
    </script>

    <script src="https://maps.googleapis.com/maps/api/js" async defer></script>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css"/>
    <script src="https://unpkg.com/leaflet@1.0.1/dist/leaflet-src.js"></script>

    <script type="text/javascript">
        <%@include file="/WEB-INF/js/Leaflet.GoogleMutant.js"%>
    </script>

    <style>
        #map {
            /*    margin: 32px; */
            /*    width: auto; */
            /*    overflow: visible; */
            width: 100vw; /*calc( 100vw - 64px );*/
            height: calc(100vh - 64px);
        }

        body {
            margin: 0
        }

    </style>
</head>
<body>

<header>
    <div class="bg-dark collapse show" id="navbarHeader" style="">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-md-7 py-4">
                    <!--<h4 class="text-white">Welcome</h4>-->
                    <p class="text-muted">You can filter parking spaces</p>
                </div>
                <div class="col-sm-4 offset-md-1 py-4">
                    <h4 class="text-white">Contact</h4>
                    <ul class="list-unstyled">
                        <li><a href="#" class="text-white">Technical support</a></li>
                        <li><a href="#" class="text-white">About</a></li>
                        <li><a href="#" class="text-white">Email us</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <a href="#" class="navbar-brand d-flex align-items-center">
                <!--<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="mr-2"><path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"></path><circle cx="12" cy="13" r="4"></circle></svg>--> <!-- картинка рядом -->
                <strong>ParkingGo</strong>
            </a>

            <li class="dropdown open">

                <sec:authorize access="!isAuthenticated()">
                    <a href="login" role="button">Log in </a>
                </sec:authorize>

                <sec:authorize access="hasAnyRole('OWNER', 'USER')">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="true">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            <sec:authentication property="principal.username" />
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/profile" target="_blank">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>My profile</a>
                        </li>
                        <li>
                            <a href="/edit">
                                <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>Edit</a>
                        </li>
                        <li>
                            <a href="/frontend/logout">
                                <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>Log out</a>
                        </li>
                    </ul>
                </sec:authorize>

                <sec:authorize access="hasRole('ADMIN')">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="true">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        <sec:authentication property="principal.username" />
                        <span class="caret"></span></a>
                </sec:authorize>

            </li>
        </div>
    </div>
</header>

<jsp:include page="/myservlet.jsp" />

<div id="map" class="map"></div>

<script type="text/javascript">

    var mapopts = {
//      zoomSnap: 0.1
    };

    var map = L.map('map', mapopts).setView([51.6886685, 39.1975811], 13);

    var roadMutant = L.gridLayer.googleMutant({
        maxZoom: 24,
        type: 'roadmap'
    }).addTo(map);

    var styleMutant = L.gridLayer.googleMutant({
        styles: [
            {elementType: 'labels', stylers: [{visibility: 'off'}]},
            {featureType: 'water', stylers: [{color: '#444444'}]},
            {featureType: 'landscape', stylers: [{color: '#eeeeee'}]},
            {featureType: 'road', stylers: [{visibility: 'off'}]},
            {featureType: 'poi', stylers: [{visibility: 'off'}]},
            {featureType: 'transit', stylers: [{visibility: 'off'}]},
            {featureType: 'administrative', stylers: [{visibility: 'off'}]},
            {featureType: 'administrative.locality', stylers: [{visibility: 'off'}]}
        ],
        maxZoom: 24,
        type: 'roadmap'
    });

    var trafficMutant = L.gridLayer.googleMutant({
        maxZoom: 24,
        type: 'roadmap'
    });
    trafficMutant.addGoogleLayer('TrafficLayer');

    L.control.layers({
        Roadmap: roadMutant,
        /*Aerial: satMutant,
        Terrain: terrainMutant,
        Hybrid: hybridMutant,
        Styles: styleMutant,*/
        Traffic: trafficMutant//,
        //Transit: transitMutant
    }, {}, {
        collapsed: false
    }).addTo(map);


    <c:forEach items="${list}" var="temp">
    var point = ["${temp.values[302]}", "${temp.values[303]}"];
    var marker25 = L.marker(point).addTo(map);
    </c:forEach>

    //marker7.bindPopup("<b>Hello!</b>").openPopup();


    var rectangle = L.rectangle(madBounds).addTo(map);

    var grid = L.gridLayer({
        attribution: 'Grid Layer',
    });

    grid.createTile = function (coords) {
        var tile = L.DomUtil.create('div', 'tile-coords');
        tile.innerHTML = [coords.x, coords.y, coords.z].join(', ');

        return tile;
    };

    map.addLayer(grid);

</script>
</body>
</html>
