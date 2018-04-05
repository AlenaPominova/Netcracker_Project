<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <title>Тестовая страница</title>
    <meta charset="utf-8" />
    <!-- Stylesheet -->
    <link href="http://yastatic.net/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/inx_styles.css" rel="stylesheet" media="all">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css" />
    <!-- JS -->
    <script src="http://yastatic.net/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://yastatic.net/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js" async defer></script>
    <script src="https://unpkg.com/leaflet@1.0.1/dist/leaflet-src.js"></script>
    <script type="text/javascript" src="js/Leaflet.GoogleMutant.js"></script>
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
            <a class="navbar-brand" href="#"><img src="img/logo.png" alt="Воронежский паркинг"></a>
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
<div class="container-fluid" style="background-color: black; padding: 0;">
    <div class="row" style="margin: 0; padding: 0; background-color: red;">
        <div class="col-md-9" style="padding: 0;">
            <div id="map" class="map"></div>
            <script type="text/javascript">
                var mapopts =  {
                    //      zoomSnap: 0.1
                };

                var map = L.map('map', mapopts).setView([51.6755,39.2089], 14);
                var trafficMutant = L.gridLayer.googleMutant({
                    maxZoom: 24,
                    type:'roadmap'
                }).addTo(map);
                trafficMutant.addGoogleLayer('TrafficLayer');

                var vrn = [51.6755, 39.2089];
                var vrn2 = [51.67, 39.2];
                var vrn3 = [51.678, 39.2];
                var marker1 = L.marker(vrn).addTo(map);
                var marker2 = L.marker(vrn2).addTo(map);
                var marker2 = L.marker(vrn3).addTo(map);

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