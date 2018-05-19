<!DOCTYPE html>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<head>
    <title>Parkings</title>
    <#setting locale="en">
    <#setting number_format="0.######">
    <#assign url = springMacroRequestContext.getPathToServlet() >

    <meta charset="utf-8" />
    <!-- Stylesheet -->
    <link href="http://yastatic.net/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css" />
    <#include "css/index_styles.css">
    <!-- JS -->
    <script src="http://yastatic.net/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://yastatic.net/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2psivVcXwi1A-thFsDpKI6aXJEe6y7bs" async defer></script>
    <script src="https://unpkg.com/leaflet@1.0.1/dist/leaflet-src.js"></script>
    <#include "js/Leaflet.GoogleMutant.js">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="loading-window">
    <div class="car">
        <div class="strike"></div>
        <div class="strike strike2"></div>
        <div class="strike strike3"></div>
        <div class="strike strike4"></div>
        <div class="strike strike5"></div>
        <div class="car-detail spoiler"></div>
        <div class="car-detail back"></div>
        <div class="car-detail center"></div>
        <div class="car-detail center1"></div>
        <div class="car-detail front"></div>
        <div class="car-detail wheel"></div>
        <div class="car-detail wheel wheel2"></div>
    </div>
    <script type="text/javascript">
        $(function(){
            var selectedEffect = "scale";
            var options = { percent: 0 };
            $('.loading-window').show(0).delay(500).animate({opacity:'0'},600, function() { $('.loading-window').hide() });
        });
    </script>
    <div class="text">
        <span>Loading</span><span class = "dots">...</span>
    </div>
</div>
<nav class="navbar navbar-default">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${url}"><img src="https://i.imgur.com/oH893fM.png" alt="Воронежский паркинг"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="#">О проекте</a></li>
                <li><a href="#">Вопрос-ответ</a></li>
                <li><a href="#">Тех.поддержка</a></li>
                <li><a href="#">Связь с нами</a></li>
            </ul>

        <@security.authorize access="isAuthenticated()">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> <@security.authentication property="principal" /> <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li>

                            <a href="${url}/profiles/${currentUserId!}">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Личный кабинет</a>
                        </li>
                        <li>
                            <form class="logout-form" method="get" action="${url}/logout">
                                <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                                <input type="submit" value="Выйти"/>
                            </form>
                        </li>
                    </ul>
                </li>
            </ul>
        </@security.authorize>
        <@security.authorize access="!isAuthenticated()">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="${url}/login">Вход/Регистрация</a>
                </li>
            </ul>
        </@security.authorize>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="container-fluid" style="background-color: black; padding: 0; margin: 0;">
    <div class="row" style="margin: 0; padding: 0;">
        <div class="col-md-9" style="padding: 0;">
            <div id="map" class="map"></div>
            <script>
                var mapopts =  {
                    //      zoomSnap: 0.1
                };
                var map = L.map('map', mapopts).setView([51.6755,39.2089], 14);
                var trafficMutant = L.gridLayer.googleMutant({
                    maxZoom: 24,
                    type:'roadmap'
                }).addTo(map);
                trafficMutant.addGoogleLayer('TrafficLayer');
                var markers = new L.FeatureGroup();
                <#list parkingsList?keys as key>
                    <#assign parkin = parkingsList?api.get(key)>
                var url = '${url}'
                var parking = [];
                parking.id = '${parkin.id}';
                parking.name = '${parkin.name}';
                parking.address = '${parkin.values?api.get(101?long)}'
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
                map.addLayer(markers);
                function createMarker(parking) {
                    var marker = L.marker([parking.latitude, parking.longitude]);
                    <@security.authorize access="!isAuthenticated()">
                        marker.bindPopup("<h4><b>" + parking.name + "</b></h4><br>" +
                            "<b>Адрес: </b>" + parking.address + "<br>" +
                            "<b>Открыта с: </b>" + parking.open_time + "<b> до:</b>" + parking.close_time + "<br>" +
                            "<b>Цена: </b>" + parking.price + " руб/час<br>" +
                            "<b>Рейтинг: </b>" + parking.rating + "<br>" +
                            "<b>Свободных мест: </b>" + parking.free_spots_count + "<br>" +
                            '<br><a href="/profiles/' + parking.owner_id + '"> Страница владельца: </a><br>');
                    </@security.authorize>
                    <@security.authorize access="isAuthenticated()">
                        marker.bindPopup("<h4><b>" + parking.name + "</b></h4><br>" +
                            "<b>Адрес: </b>" + parking.address + "<br>" +
                            "<b>Открыта с: </b>" + parking.open_time + "<b> до:</b>" + parking.close_time + "<br>" +
                            "<b>Цена: </b>" + parking.price + " руб/час<br>" +
                            "<b>Рейтинг: </b>" + parking.rating + "<br>" +
                            "<b>Свободных мест: </b>" + parking.free_spots_count + "<br>" +
                                '<br><a href="${url}/profiles/' + parking.owner_id + '"> Страница владельца: </a>' +
                                '<br><a href="${url}/parkings/' + parking.id + '?take"> Взять в аренду: </a>' +
                                // '<form method="post" action="' + url + '/parkings/' + parking.id + '?take">' +
                                // '<input type="hidden" name="_method" value="put"/>' +
                                // '<input type="submit" value="Взять в аренду" />' +
                                // '</form>' +
                            "<button type=\"submit\" class=\"btn btn-default\">Взять в аренду</button>");
                    </@security.authorize>
                    markers.addLayer(marker);
                }
                function createFilterMarker(parking) {
                    var marker = L.marker([parking.latitude, parking.longitude]);
                    marker.bindPopup("<h4><b>" + parking.name + "</b></h4><br>" +
                            "<b>Адрес: </b>" + parking.address + "<br>" +
                            "<b>Открыта с: </b>" + parking.open_time + "<b> до:</b>" + parking.close_time + "<br>" +
                            "<b>Цена: </b>" + parking.price + " руб/час<br>" +
                            "<b>Рейтинг: </b>" + parking.rating + "<br>" +
                            "<b>Свободных мест: </b>" + parking.free_spots_count + "<br>" +
                            "<a href=\"/objects/' + parking.owner_id + '\">Страница владельца</a><br><br>" +
                            "<button type=\"submit\" class=\"btn btn-default\">Взять в аренду</button>");
                    /* Check valid for parking spot */
                    if (
                            checkOnValid('#fromPriceInput', '#toPriceInput', parking.price) &&
                            checkOnValid('#fromRatingInput', '#toRatingInput', parking.rating)
                    )
                        markers.addLayer(marker);
                }
                function checkOnValid(idAttFrom, idAttTo, valueToComp) {
                    if ($(idAttFrom).val() < $(idAttTo).val()){
                        if ($(idAttFrom).val() <= valueToComp && $(idAttTo).val() >= valueToComp){
                            return true;
                        }
                    } else {
                        if ($(idAttFrom).val() <= valueToComp)
                            return true;
                    }
                    return false;
                }
                function removeAllMarkers(){
                    map.removeLayer(markers);
                    markers = new L.FeatureGroup();
                <#list parkingsList?keys as key>
                    <#assign parkin = parkingsList?api.get(key)>
                    var url = '${url}'
                    var parking = [];
                    parking.name = '${parkin.name}';
                    parking.address = '${parkin.values?api.get(101?long)}'
                    parking.latitude = '${parkin.values?api.get(301?long)}'
                    parking.longitude = '${parkin.values?api.get(302?long)}'
                    parking.price = '${parkin.values?api.get(304?long)}'
                    parking.open_time = '${(parkin.dateValues?api.get(305?long))?time}'
                    parking.close_time = '${parkin.dateValues?api.get(306?long)?time}'
                    parking.free_spots_count = '${parkin.values?api.get(307?long)}'
                    parking.rating = '${parkin.values?api.get(100?long)?number}'
                    parking.status = '${parkin.listValues?api.get(308?long)}'
                    parking.owner_id = '${parkin.references?api.get(300?long)}'
                    createFilterMarker(parking);
                </#list>
                    map.addLayer(markers);
                }
            </script>
        </div>
        <div class="col-md-3 filter" style="background-color: #0e182e;height: 91.5vh">
            <h1>ФИЛЬТР ПАРКОВОК</h1>
            <div class="filter-choose" style="margin-top: 40px; margin-left: 20px;">
                <div class="form-group">
                    <label class="col-md-3 control-label" style="padding-left: 0px; margin-left: 0px;">Время от</label>
                    <div class="col-md-4">
                        <input class="form-control" type="time" value="13:45:00" id="example-time-input">
                    </div>
                    <label class="col-md-1 control-label">до</label>
                    <div class="col-md-4">
                        <input class="form-control" type="time" value="13:45:00" id="example-time-input">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label" style="padding-left: 0px; margin-left: 0px;">Цена от</label>
                    <div class="col-md-4">
                        <input class="form-control" type="number" value="0" id="fromPriceInput">
                    </div>
                    <label class="col-md-1 control-label">до</label>
                    <div class="col-md-4">
                        <input class="form-control" type="number" value="100" id="toPriceInput">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label" style="padding-left: 0px; margin-left: 0px;">Рейтинг от</label>
                    <div class="col-md-4">
                        <input class="form-control" type="number" value="0" id="fromRatingInput">
                    </div>
                    <label class="col-md-1 control-label">до</label>
                    <div class="col-md-4">
                        <input class="form-control" type="number" value="5" id="toRatingInput">
                    </div>
                </div>
                <div class="form-group">
                    <div class="main-checkbox">
                        <input value="None" id="checkbox1" name="check" type="checkbox">
                        <label for="checkbox1"></label>
                    </div>
                    <span class="text">Показывать только доступные</span>
                </div>
                <div class="form-group">
                    <button class="btn btn-default" onclick="removeAllMarkers()">Применить фильтр</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>