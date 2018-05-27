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
        <div class="col-lg-9" style="padding: 0;">
            <div id="map" class="map"></div>
            <script>
                var mapopts =  {};
                var map = L.map('map', mapopts).setView([51.6755,39.2089], 14);
                var trafficMutant = L.gridLayer.googleMutant({maxZoom: 24, type:'roadmap'}).addTo(map);
                trafficMutant.addGoogleLayer('TrafficLayer');

                var greenIcon = L.icon({iconUrl: 'https://i.imgur.com/HlgBDB6.png', iconSize: [60,50]});
                var blueIcon = L.icon({iconUrl: 'https://i.imgur.com/KU4ct7n.png', iconSize: [60,50]});
                var redIcon = L.icon({iconUrl: 'https://i.imgur.com/yU4qi9n.png', iconSize: [60,50]});

                var markers = new L.FeatureGroup();

                <#list parkingsList?keys as key>
                    <#assign parkin = parkingsList?api.get(key)>
                var url = '${url}'
                var parking = [];
                parking.id = '${parkin.id}';
                parking.name = '${parkin.name}';
                parking.address = '${parkin.values?api.get(101?long)}';
                parking.latitude = '${parkin.values?api.get(301?long)}';
                parking.longitude = '${parkin.values?api.get(302?long)}';
                parking.price = '${parkin.values?api.get(304?long)}';
                parking.open_time = '${(parkin.dateValues?api.get(305?long))?time}';
                parking.close_time = '${parkin.dateValues?api.get(306?long)?time}';
                parking.free_spots_count = '${parkin.values?api.get(307?long)}';
                parking.rating = '${parkin.values?api.get(100?long)?number}';
                parking.status = '${parkin.listValues?api.get(308?long)}';
                parking.owner_id = '${parkin.references?api.get(300?long)}';
                parking.overtime = '${parkin.listValues?api.get(309?long)!'отсутствует'}';
                parking.koeff = '${parkin.values?api.get(310?long)!'отсутствует'}';
                parking.parking_photo = '${parkin.values?api.get(102?long)!'https://renderman.pixar.com/assets/camaleon_cms/image-not-found-4a963b95bf081c3ea02923dceaeb3f8085e1a654fc54840aac61a57a60903fef.png'}';
                createMarker(parking);
                </#list>

                map.addLayer(markers);

                function createMarker(parking) {
                    var marker;
                    var isAnonymous;

                    <@security.authorize access="isAuthenticated()">
                        isAnonymous = false;
                    </@security.authorize>
                    <@security.authorize access="!isAuthenticated()">
                        isAnonymous = true;
                    </@security.authorize>

                    if (parking.status == "Free") {
                        if (parking.free_spots_count > 1) {
                            marker = fillMarker(parking, 1, isAnonymous);
                        } else {
                            marker = fillMarker(parking, 0, isAnonymous);
                        }
                    } else {
                        marker = fillMarker(parking, 2, isAnonymous);
                    }
                    markers.addLayer(marker);
                }

                function createFilterMarker(parking) {
                    var isAnonymous;
                    var marker;
                    <@security.authorize access="isAuthenticated()">
                        isAnonymous = false;
                    </@security.authorize>
                    <@security.authorize access="!isAuthenticated()">
                        isAnonymous = true;
                    </@security.authorize>

                    if (parking.status == "Free") {
                        if (parking.free_spots_count > 1) {
                            marker = fillMarker(parking, 1, isAnonymous);
                        } else {
                            marker = fillMarker(parking, 0, isAnonymous);
                        }
                    } else {
                        marker = fillMarker(parking, 2, isAnonymous);
                    }
                    /* Check valid for parking spot */
                    if (
                            checkOnValid('#fromPriceInput', '#toPriceInput', parseInt(parking.price)) &&
                            checkOnValid('#fromRatingInput', '#toRatingInput', parseInt(parking.rating))&&
                            checkOnValidTime(parking.open_time, parking.close_time) &&
                            checkCheckboxes(parking.free_spots_count)
                    )
                        markers.addLayer(marker);
                }

                // Status properties:
                // 0 - free small parking spot
                // 1 - free big parking spot
                // 2 - occupied parking spot
                function fillMarker(parking, status, isAnonymous) {
                    var _marker;
                    var anonymousStr =  "<img src=" + parking.parking_photo + " width='100' height='60'>" +
                                        "<h4><b>" + parking.name + "</b></h4><br>" +
                                        "<b>Адрес: </b>" + parking.address + "<br>" +
                                        "<b>Открыта с: </b>" + parking.open_time + "<b> до:</b>" + parking.close_time + "<br>" +
                                        "<b>Цена: </b>" + parking.price + " руб/час<br>" +
                                        "<b>Рейтинг: </b>" + parking.rating + "<br>" +
                                        "<b>Овертайм: </b>" + parking.overtime + "<br>";

                    if (parking.overtime == 'Повышенние стоимости аренды')
                        anonymousStr += "<b>Коэффициент: </b>x" + parking.koeff + "<br>";

                    anonymousStr += "<b>Свободных мест: </b>" + parking.free_spots_count + "<br>";

                    var userFree =  '<br><a href="${url}/profiles/' + parking.owner_id + '"> Страница владельца: </a>' +
                                    '<form method="get" action="' + url + '/parkings/' + parking.id + '/rent">' +
                                    '<button type="submit" class="btn btn-rent">АРЕНДОВАТЬ</button>'+
                                    '</form>';

                    var userBig =   '<br><a href="${url}/profiles/' + parking.owner_id + '"> Страница владельца: </a>' +
                                    '<form method="get" action="' + url + '/parkings/' + parking.id + '/rent">' +
                                    '<button type="submit" style="background-color: #d8d8d8; border: 1px solid #d8d8d8; color: d8d8d8;" class="btn btn-rent" disabled>АРЕНДОВАТЬ</button>' +
                                    '</form>';

                    switch (status){
                        case 0:
                            _marker = L.marker([parking.latitude, parking.longitude], {icon: greenIcon});
                            _marker.bindPopup(isAnonymous ? anonymousStr : anonymousStr + userFree);
                            break;
                        case 1:
                            _marker = L.marker([parking.latitude, parking.longitude], {icon: blueIcon});
                            _marker.bindPopup(isAnonymous ? anonymousStr : anonymousStr + userBig);
                            break;
                        case 2:
                            _marker = L.marker([parking.latitude, parking.longitude], {icon: redIcon});
                            _marker.bindPopup(isAnonymous ? anonymousStr : anonymousStr + userBig);
                            break;
                        default:
                            break;
                    }
                    return _marker;
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

                function checkOnValidTime(openTime, closeTime) {
                    var ot = openTime.toString().split(':');
                    var ct = closeTime.toString().split(':');
                    var fti = $('#fromTimeInput').val().toString().split(':');
                    var tti = $('#toTimeInput').val().toString().split(':');

                    var mOt = parseInt(ot[0])*60+parseInt(ot[1]);
                    var mCt = parseInt(ct[0])*60+parseInt(ct[1]);
                    var mFti = parseInt(fti[0])*60+parseInt(fti[1]);
                    var mTti = parseInt(tti[0])*60+parseInt(tti[1]);

                    if (mFti < mTti){
                        if (mFti <= mOt && mTti >= mCt){
                            return true;
                        }
                    } else {
                        if (mFti <= mOt)
                            return true;
                    }
                    return false;
                }
                function removeAllMarkers(){
                    map.removeLayer(markers);
                    markers = new L.FeatureGroup();
                <#list parkingsList?keys as key>
                    <#assign parkin = parkingsList?api.get(key)>
                    var url = '${url}';
                    var parking = [];
                    parking.name = '${parkin.name}';
                    parking.address = '${parkin.values?api.get(101?long)}';
                    parking.latitude = '${parkin.values?api.get(301?long)}';
                    parking.longitude = '${parkin.values?api.get(302?long)}';
                    parking.price = '${parkin.values?api.get(304?long)}';
                    parking.open_time = '${(parkin.dateValues?api.get(305?long))?time}';
                    parking.close_time = '${parkin.dateValues?api.get(306?long)?time}';
                    parking.free_spots_count = '${parkin.values?api.get(307?long)}';
                    parking.rating = '${parkin.values?api.get(100?long)?number}';
                    parking.status = '${parkin.listValues?api.get(308?long)}';
                    parking.owner_id = '${parkin.references?api.get(300?long)}';
                    parking.overtime = '${parkin.listValues?api.get(309?long)!'отсутствует'}';
                    parking.koeff = '${parkin.values?api.get(310?long)!'отсутствует'}';
                    parking.parking_photo = '${parkin.values?api.get(102?long)!'https://renderman.pixar.com/assets/camaleon_cms/image-not-found-4a963b95bf081c3ea02923dceaeb3f8085e1a654fc54840aac61a57a60903fef.png'}';
                    createFilterMarker(parking);
                </#list>
                    map.addLayer(markers);
                }

                function checkCheckboxes(free_spots_count){
                    if ($("#checkbox1").prop('checked'))
                        if (free_spots_count == 0)
                            return false;
                    if ($("#checkbox2").prop('checked'))
                        if (free_spots_count > 1)
                            return false;
                    if ($("#checkbox3").prop('checked'))
                        if (free_spots_count < 2)
                            return false;
                    return true;
                }
            </script>
        </div>
        <div class="col-lg-3 filter">
            <div class="filter-choose">
                <h1>ФИЛЬТР ПАРКОВОК</h1>
                <p class="filter-label"><span class="glyphicon glyphicon-time" style="padding-right: 10px;"></span> Свободное время </p>
                <div class="form-group">
                    <p class="label"> От</p>
                    <input class="input-type-time" type="time" value="01:45:00" id="fromTimeInput">
                    <p class="label" style="margin-left: 10px;"> До </p>
                    <input class="input-type-time" type="time" value="23:45:00" id="toTimeInput">
                </div>

                <p class="filter-label"><span class="glyphicon glyphicon-usd" style="padding-right: 10px;"></span> Стоимость парковки </p>
                <div class="form-group">
                    <p class="label"> От</p>
                    <input class="input-type-price" type="number" min="1" max="9999" step="1" value="1" id="fromPriceInput">
                    <p class="label" style="margin-left: 10px;"> До </p>
                    <input class="input-type-price" type="number" min="1" max="9999" step="1" value="1000" id="toPriceInput">
                </div>

                <p class="filter-label"><span class="glyphicon glyphicon-star" style="padding-right: 10px;"></span> Рейтинг парковки </p>
                <div class="form-group">
                    <p class="label"> От</p>
                    <input class="input-type-price" type="number" min="1" max="5" step="1" value="1" id="fromRatingInput">
                    <p class="label" style="margin-left: 10px;"> До </p>
                    <input class="input-type-price" type="number" min="1" max="5" step="1" value="5" id="toRatingInput">
                </div>

                <div class="form-group" style="text-align: left;">
                    <div class="main-checkbox">
                        <input value="confirmed" id="checkbox1" name="status" type="checkbox">
                        <label for="checkbox1"></label>
                    </div>
                    <span class="text">Показывать только свободные</span>
                </div>
                <div class="form-group" style="text-align: left;">
                    <div class="main-checkbox">
                        <input value="confirmed" id="checkbox2" name="status" type="checkbox">
                        <label for="checkbox2"></label>
                    </div>
                    <span class="text">Показывать только частные</span>
                </div>
                <div class="form-group" style="text-align: left;">
                    <div class="main-checkbox">
                        <input value="confirmed" id="checkbox3" name="status" type="checkbox">
                        <label for="checkbox3"></label>
                    </div>
                    <span class="text">Показывать только большие</span>
                </div>

                <div class="form-group">
                    <button class="btn btn-default" onclick="removeAllMarkers()">Применить фильтр</button>
                </div>
            </div>

            <div class="warning" style="margin-top: 20px; ">
                <h1>МАРКЕРЫ</h1>
                <div class="form-group" style="text-align: left;">
                    <img src="https://i.imgur.com/HlgBDB6.png" width="50" height="40">
                    <p class="label"> - Свободная парковка</p>
                </div>
                <div class="form-group" style="text-align: left;">
                    <img src="https://i.imgur.com/KU4ct7n.png" width="50" height="40">
                    <p class="label"> - Большая коммерческая парковка</p>
                </div>
                <div class="form-group" style="text-align: left;">
                    <img src="https://i.imgur.com/yU4qi9n.png" width="50" height="40">
                    <p class="label"> - Занятая парковка</p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>