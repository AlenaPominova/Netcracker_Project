<!DOCTYPE html>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring/>
<html>
<head>
<#setting locale="en">
<#setting number_format="0.######">
<#assign url = springMacroRequestContext.getPathToServlet() >
    <meta charset="utf-8" />
    <!-- Stylesheet -->
    <link href="http://yastatic.net/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<#include "css/parking_styles.css">
    <!-- JS -->
    <script src="http://yastatic.net/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://yastatic.net/bootstrap/3.3.4/js/bootstrap.min.js"></script>
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
                            <a href="${url}" target="_blank">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>Личный кабинет
                            </a>
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
<div class="container">
    <h1 class="title">Изменение данных о парковке</h1>
    <div class="line"></div>
    <div class="row" style="padding-left: 15px; padding-right: 15px; padding-top: 50px;">
        <div class="col-lg-5 col-md-5" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
            <div class="col-lg-12 col-md-4" style="padding-left: 0px;">
                <img src="http://lutsk.rayon.in.ua/upload/news/1/2018-03/152163432386/t_1_parkovka.jpg" style="width: 400px; height: 300px; border-radius: 50px;">
            </div>
        </div>
        <div class="col-lg-7 col-md-7" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
            <div class="col-lg-12 col-md-12">
                <div class="col-lg-12 col-md-12 about-user" style="padding-left: 0px; margin-top: 0px;">
                    <h2 style="padding-bottom: 20px;">${parking.name}</h2>
                    <h3><b>Адрес:</b> ${parking.values?api.get(101?long)}</h3>
                    <h3><b>Координаты:</b>${parking.values?api.get(301?long)} , ${parking.values?api.get(302?long)}</h3>
                    <h3><b>Стоимость:</b> </h3>
                    <div class="form-group">
                        <form method="post" action="/parkings/${parking.id}/edit">
                            <input type="hidden" name="_method" value="put"/>
                            <@spring.formInput "parking.values['${304?long}']", "class='form-control' min='1' max='9999' step='1'", "number"/>
                            <p class="label"> рублей/час </p>
                            <h3><b>Свободные часы:</b> </h3>
                            <p class="label">От </p>
                            <@spring.formInput "parking.dateValues['${305?long}']", "class='form-control'", "time"/>
                            <p class="label">До </p>
                            <@spring.formInput "parking.dateValues['${306?long}']", "class='form-control'", "time"/>

                            <h3><b>Свободно мест:</b> ${parking.values?api.get(307?long)}</h3>
                            <h3><b>Рейтинг парковки:</b> ${parking.values?api.get(100?long)?number}</h3>
                            <h3><b>Статус:</b> ${parking.listValues?api.get(308?long)}</h3>

                            <button class="btn-chg" type="submit">Сохранить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>