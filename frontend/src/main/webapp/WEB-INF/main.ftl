<#ftl encoding="utf-8">
<!doctype html>
<html>
<head>
    <title>Тестовая страница</title>
    <!-- Stylesheet -->
    <link href="http://yastatic.net/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css" />
    <!-- JS -->
    <script src="http://yastatic.net/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://yastatic.net/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2psivVcXwi1A-thFsDpKI6aXJEe6y7bs" async defer></script>
    <script src="https://unpkg.com/leaflet@1.0.1/dist/leaflet-src.js"></script>
    <#include "css/inx_styles.css">
    <#include "js/Leaflet.GoogleMutant.js">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img src="https://2.downloader.disk.yandex.ru/preview/63f5c51403302a9b55553a7b0fc16cfdb911b33f8c5af56505d47adef3bc57e1/inf/eS5gAm22UDiYgvhUyY1tr8IH7UqxXoP0fGKdHch3EC-Pa0AujPi0S_lHQR8TYJ8-VHwR3sYD2xCkU0ZxQ9xLaA%3D%3D?uid=195905471&filename=logo.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&tknv=v2&size=1280x732" alt="Воронежский паркинг"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="#">О проекте</a></li>
                <li><a href="#">Вопрос-ответ</a></li>
                <li><a href="#">Тех.поддержка</a></li>
                <li><a href="#">Связь с нами</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Username <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="http://www.fgruber.ch/" target="_blank">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Личный кабинет</a>
                        </li>
                        <li>
                            <a href="/logout">
                                <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Выйти</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="container-fluid" style="background-color: black; padding: 0; margin: 0;">
    <div class="row" style="margin: 0; padding: 0; background-color: red;">
        <div class="col-md-9" style="padding: 0;">
            <div id="map" class="map"></div>
            <script type="text/javascript">
                function getPrice(info) {
                    for (var i = 0; i < info.length; i++){
                        if (info[i].id == 304)
                            return info[i].value;
                    }
                }

                function getLatitude(info) {
                    for (var i = 0; i < info.length; i++){
                        if (info[i].id == 301)
                            return info[i].value;
                    }
                }

                function getLongitude(info) {
                    for (var i = 0; i < info.length; i++){
                        if (info[i].id == 302)
                            return info[i].value;
                    }
                }
                
                var mapopts =  {
                    //      zoomSnap: 0.1
                };

                var map = L.map('map', mapopts).setView([51.6755,39.2089], 14);
                var trafficMutant = L.gridLayer.googleMutant({
                    maxZoom: 17,
                    minZoom: 9,
                    type:'roadmap'
                }).addTo(map);
                trafficMutant.addGoogleLayer('TrafficLayer');

                $.ajax({
                    type: 'GET',
                    url: 'http://localhost:8082/getByObjectType/asd',
                    contentType: 'text/plain',
                    xhrFields: { withCredentials: false },
                    headers: {},
                    success: function(data) {
                        for (var i = 0; i < data.parkings.length; i++){
                            var temp = [getLatitude(data.parkings[i].values), getLongitude(data.parkings[i].values)];
                            var marker = L.marker(temp).addTo(map);
                            marker.bindPopup("<h4><b>Парковка №" + (data.parkings[i].id).toString() + "</b></h4><br>" +
                                    "<b>Цена:</b>" + getPrice(data.parkings[i].values) + "руб/час<br>" +
                                    "<b>Владелец:</b> <a href=''>Евгений Карпов</a>");
                        }
                    },
                    error: function() {
                        alert('Неизвестная валютная пара');
                    }
                });

//                var vrn2 = [51.67, 39.2];
//                var marker2 = L.marker(vrn2).addTo(map);
//                marker2.bindPopup("" +
//                        "<h4><b>Парковка №255</b></h4><br>" +
//                        "<b>Открыта:</b> с 00:00 до 00:00<br>" +
//                        "<b>Цена:</b> 100 руб/час<br>" +
//                        "<b>Рейтинг:</b> 4,65<br>" +
//                        "<br>" +
//                        "<b>Владелец:</b> <a href=''>Евгений Карпов</a>");
            </script>
        </div>
        <div class="col-md-3 filter" style="background-color: #0e182e;height: 91.5vh">
            <h1>ФИЛЬТР ПАРКОВОК</h1>
            <div class="filter-choose" style="margin-top: 40px; margin-left: 20px;">
                <form class="form-horizontal">
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
                            <input class="form-control" type="number" value="0" id="example-number-input">
                        </div>
                        <label class="col-md-1 control-label">до</label>
                        <div class="col-md-4">
                            <input class="form-control" type="number" value="100" id="example-number-input">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label" style="padding-left: 0px; margin-left: 0px;">Рейтинг от</label>
                        <div class="col-md-4">
                            <input class="form-control" type="number" value="0" id="example-number-input">
                        </div>
                        <label class="col-md-1 control-label">до</label>
                        <div class="col-md-4">
                            <input class="form-control" type="number" value="5" id="example-number-input">
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
                        <button type="submit" class="btn btn-default">Применить фильтр</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>