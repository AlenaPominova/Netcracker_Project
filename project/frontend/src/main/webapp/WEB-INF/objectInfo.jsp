<%--
  Created by IntelliJ IDEA.
  User: Алена
  Date: 15.03.2018
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>

    <script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js">
    </script>

    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBfO7zYPzwC-GZWyqvnbPHNVH7U1aloWrg&callback=initMap">
    </script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main-style.css"/>"/>

    <style>
        <%@include file='/resources/css/main-style.css' %>
    </style>

    <title>Hello front</title>
</head>
<body>
<div class="parent">

    <div class="header">карта</div>

    <div class="main">

        <div id="map"></div>

        <div class="right-panel"></div>
    </div>

    <div class="footer"> footer</div>
</div>

<script>
    function initMap() {
        var uluru = {lat: 51.67204, lng: 39.1843};
        var mark = {lat: ${object.latitude}, lng: ${object.longitude}};
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 13,
            center: uluru
        });
        var marker = new google.maps.Marker({
            position: mark,
            map: map
        });

//        var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
//
//        var markers = locations.map(function(location, i) {
//            return new google.maps.Marker({
//                position: location,
//                label: labels[i % labels.length]
//            });
//        });

//        var markerCluster = new MarkerClusterer(map, markers,
//            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
//
    }
//    var locations = [
//        {lat: -31.563910, lng: 147.154312},
//        {lat: -33.718234, lng: 150.363181},
//        {lat: -33.727111, lng: 150.371124},
//        {lat: -33.848588, lng: 151.209834},
//        {lat: -33.851702, lng: 151.216968},
//        {lat: -34.671264, lng: 150.863657},
//        {lat: -35.304724, lng: 148.662905},
//        {lat: -36.817685, lng: 175.699196},
//        {lat: -36.828611, lng: 175.790222},
//        {lat: -37.750000, lng: 145.116667},
//        {lat: -37.759859, lng: 145.128708},
//        {lat: -37.765015, lng: 145.133858},
//        {lat: -37.770104, lng: 145.143299},
//        {lat: -37.773700, lng: 145.145187},
//        {lat: -37.774785, lng: 145.137978},
//        {lat: -37.819616, lng: 144.968119},
//        {lat: -38.330766, lng: 144.695692},
//        {lat: -39.927193, lng: 175.053218},
//        {lat: -41.330162, lng: 174.865694},
//        {lat: -42.734358, lng: 147.439506},
//        {lat: -42.734358, lng: 147.501315},
//        {lat: -42.735258, lng: 147.438000},
//        {lat: -43.999792, lng: 170.463352}
//    ]
</script>
</body>
</html>
