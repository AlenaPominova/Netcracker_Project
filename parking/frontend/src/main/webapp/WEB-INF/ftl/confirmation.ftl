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
            <a class="navbar-brand" href="${main_url}"><img src="https://i.imgur.com/oH893fM.png" alt="Воронежский паркинг"></a>
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
                            <a href="${main_url}/profiles/${currentUserId!}" target="_blank">
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
    <div class="bg"></div>
    <h1 class="title">Аренда парковки</h1>
    <div class="line"></div>
    <div class="row" style="padding-left: 15px; padding-right: 15px; padding-top: 50px;">
        <div class="col-lg-5 col-md-5" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
            <div class="col-lg-12 col-md-4" style="padding-left: 0px;">
                <img src="${parking.values?api.get(102?long)!"https://renderman.pixar.com/assets/camaleon_cms/image-not-found-4a963b95bf081c3ea02923dceaeb3f8085e1a654fc54840aac61a57a60903fef.png"}" style="width: 400px; height: 300px; border-radius: 50px;">
            </div>
            <div class="col-lg-12 col-md-12 about-user" style="padding-left: 0px; margin-top: 30px;">
                <h2>${parking.name}</h2>
                <h3><b>Адрес:</b> ${parking.values?api.get(101?long)}</h3>
                <h3><b>Координаты:</b> ${parking.values?api.get(301?long)} , ${parking.values?api.get(302?long)}</h3>
                <h3><b>Стоимость:</b> ${parking.values?api.get(304?long)} рублей/час</h3>
                <h3><b>Свободные часы:</b> с ${(parking.dateValues?api.get(305?long))?time} до ${parking.dateValues?api.get(306?long)?time}</h3>
                <h3><b>Свободно мест:</b> ${parking.values?api.get(307?long)}</h3>
                <h3><b>Рейтинг парковки:</b> ${parking.values?api.get(100?long)?number}</h3>
                <h3><b>Владелец:</b> ${parking.references?api.get(300?long)}</h3>
                <h3><b>Статус:</b> ${parking.listValues?api.get(308?long)}</h3>
            </div>
        </div>
        <div class="col-lg-7 col-md-7" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
            <div class="col-lg-12 col-md-12">
                <div class="span4">
                    <div class="panel panel-danger">
                        <div class="panel-heading">Соглашение об аренде парковочного места</div>
                        <div class="panel-body panel-height">
                            <div class="rules">
                                <ol>
                                    <li>Уважайте других коммиттеров.</li>
                                    <li>Уважайте других участников проекта.</li>
                                    <li>Обсудите любые значимые изменения до коммита.</li>
                                    <li>Уважайте существующих мейнтейнеров (указанных в поле MAINTAINER файлов Makefile или в файле MAINTAINER в корневом каталоге репозитория).</li>
                                    <li>Уважайте существующих мейнтейнеров (указанных в поле MAINTAINER файлов Makefile или в файле MAINTAINER в корневом каталоге репозитория).</li>
                                    <li>Любое спорное изменение необходимо откатить (back out) в ожидании решения, если того требует мейнтейнер. Вопросы безопасности могут перекрывать мнение мейнтейнера, если так решит Security Officer.</li>
                                    <li>Изменения вносятся в ветвь FreeBSD-CURRENT до FreeBSD-STABLE, за исключением случаев, прямо разрешенных выпускающими инженерами или неприменимости изменения к FreeBSD-CURRENT. Любое нетривиальное и не срочное изменение должно быть выдержано в FreeBSD-CURRENT в течение по крайней мере 3 дней перед переносом, чтобы его могли адекватно протестировать. Выпускающие инженеры обладают той же властью в ветви FreeBSD-STABLE, что и мейнтейнеры (см. правило 5).</li>
                                    <li>Не пререкайтесь с другими коммиттерами публично: это дурно выглядит. Если вам необходимо с чем-либо <<категорически не согласиться>>, делайте это личной почтой.</li>
                                    <li>Соблюдайте все периоды заморозки кода (core freeze), а также своевременно читайте списки рассылки committers и developers, чтобы быть в курсе расписания таких периодов.</li>
                                    <li>Если вы сомневаетесь в какой-либо процедуре, сначала спросите!</li>
                                    <li>Тестируйте свои изменения перед коммитом.</li>
                                    <li>Не производите коммит в деревья src/contrib, src/crypto и src/sys/contrib без прямого разрешения (approval) соответствующего мейнтейнера(ов).</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-12 col-md-12">
                <div class="form-group">
                    <div class="main-checkbox">
                        <input value="None" id="checkbox1" name="check" type="checkbox">
                        <label for="checkbox1"></label>
                    </div>
                    <span class="text">С правилами об аренде парковочного места ознакомлен и принимаю их.</span>
                </div>
                <a href="${url}/parkings/${parking.id}/rent?status=confirmed">сними меня</a>
                <form method="LINK" action="${url}/parkings/${parking.id}/rent?status=confirmed">
                    <button type="submit" class="btn-chg">АРЕНДОВАТЬ</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>