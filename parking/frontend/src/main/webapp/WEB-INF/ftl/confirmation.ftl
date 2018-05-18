<!DOCTYPE html>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<html>
<head>
<#setting locale="en">
<#setting number_format="0.######">
<#assign url = springMacroRequestContext.getPathToServlet() >
    <meta charset="utf-8" />
    <!-- Stylesheet -->
    <link href="http://yastatic.net/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<#include "css/prof_styles.css">
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
<div class="container" style="padding-left: 0px; padding-right: 0px;">
    <div class="line"></div>
    <div class="row" style="padding-left: 15px; padding-right: 15px; padding-top: 50px;">
        <div class="col-lg-6" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
            <div class="col-lg-12">
                <div class="col-lg-5 parking-img">
                    <img src="${parking.values?api.get(102?long)!"https://renderman.pixar.com/assets/camaleon_cms/image-not-found-4a963b95bf081c3ea02923dceaeb3f8085e1a654fc54840aac61a57a60903fef.png"}" style="width: 200px; height: 150px; border-radius: 10px;">
                </div>
                <div class="col-lg-7 parking">
                    <h2>${parking.name}</h2>
                    <h3 style="margin-top: 5px;"><b>Адрес:</b> ${parking.values?api.get(101?long)}</h3>
                    <h3 style="margin-top: 5px;"><b>Координаты:</b> ${parking.values?api.get(301?long)} , ${parking.values?api.get(302?long)}</h3>
                    <h3 style="margin-top: 5px;"><b>Стоимость:</b> ${parking.values?api.get(304?long)} рублей/час</h3>
                    <h3><b>Свободные часы:</b> с ${(parking.dateValues?api.get(305?long))?time} до ${parking.dateValues?api.get(306?long)?time}</h3>
                    <h3><b>Свободно мест:</b> ${parking.values?api.get(307?long)}</h3>
                    <h3><b>Рейтинг парковки:</b> ${parking.values?api.get(100?long)?number}</h3>
                    <h3><b>Статус:</b> ${parking.listValues?api.get(308?long)}</h3>
                    <button class="btn-chg">РЕДАКТИРОВАТЬ</button>

                    <form method="post" action="' + url + '/parkings/' + parking.id + '?take">' +
                        <input type="hidden" name="_method" value="put"/>' +
                        <input type="submit" value="Взять в аренду" />' +
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>