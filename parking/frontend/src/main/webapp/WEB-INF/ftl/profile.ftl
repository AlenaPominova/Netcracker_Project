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
<div class="bg"></div>
<@security.authentication property="principal" var="name" scope="page" />
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
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${.globals.name}<span class="caret"></span></a>
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
    <h1 class="title">Личный кабинет</h1>
    <div class="line"></div>
    <div class="row" style="padding-left: 15px; padding-right: 15px; padding-top: 50px;">
        <div class="col-lg-6 col-md-6" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
            <div class="col-lg-4 col-md-4" style="padding-left: 0px;">
                <img src="http://www.hexatar.com/gallery/png/160329_071328_m0491d9eba1_avatar.png" style="width: 200px; height: 200px; border-radius: 50px;">
            </div>
            <div class="col-lg-8 col-md-6 user-info" style="padding-top: 0px; margin-top: 0px;">
                <#if user.typeId == 2>
                    <h2>${user.name}</h2><p>( Владелец )</p>
                <#else>
                    <h2>${user.name}</h2><p>( Пользователь )</p>
                </#if>
                    <h3 style="margin-top: 20px;"><b>Номер телефона:</b> ${user.values?api.get(201?long)}</h3>
                    <h3><b>E-mail:</b> ${user.values?api.get(202?long)}</h3>
                    <h3><b>Рейтинг:</b> 5</h3>
                <#if user.values?api.get(202?long) == .globals.name>
                    <button class="btn-chg">РЕДАКТИРОВАТЬ</button>
                </#if>
            </div>
            <div class="col-lg-12 col-md-12 about-user" style="padding-left: 0px; margin-top: 30px;">
                <h2>О себе:</h2>
                <h3 style="margin-top: 20px;">
                <#if user.description??>
                    К сожалению, информация отсутствует.
                <#else>
                ${user.description}
                </#if>
                </h3>
            </div>
        </div>
        <div class="col-lg-6 col-md-6" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
            <div class="col-lg-12 about-parking" style="padding-top: 0px; margin-top: 0px;">
                <h2>Список парковок:</h2>
            </div>
        <#list ownedParkings?keys as key>
            <#assign parkin = ownedParkings?api.get(key)>
            <#if user.id == parkin.references?api.get(300?long)>
                <div class="col-lg-12 col-md-12">
                    <div class="col-lg-5 col-md-5 parking-img">
                        <img class="parking-photo" src="http://lutsk.rayon.in.ua/upload/news/1/2018-03/152163432386/t_1_parkovka.jpg">
                        <#if user.values?api.get(202?long) == .globals.name>
                            <#if parkin.listValues?api.get(308?long) == "Occupied">
                                <button class="btn-chg">ЭВАКУИРОВАТЬ</button>
                            </#if>
                            <button class="btn-chg">РЕДАКТИРОВАТЬ</button>
                        </#if>
                    </div>
                    <div class="col-lg-7 col-md-7 parking">
                        <h2>${parkin.name}</h2>
                        <h3><b>Адрес:</b> ${parkin.values?api.get(101?long)}</h3>
                        <h3><b>Координаты:</b> ${parkin.values?api.get(301?long)} , ${parkin.values?api.get(302?long)}</h3>
                        <h3><b>Стоимость:</b> ${parkin.values?api.get(304?long)} рублей/час</h3>
                        <h3><b>Свободные часы:</b> с ${(parkin.dateValues?api.get(305?long))?time} до ${parkin.dateValues?api.get(306?long)?time}</h3>
                        <h3><b>Свободно мест:</b> ${parkin.values?api.get(307?long)}</h3>
                        <h3><b>Рейтинг парковки:</b> ${parkin.values?api.get(100?long)?number}</h3>
                        <h3><b>Владелец:</b> ${parkin.references?api.get(300?long)}</h3>
                        <h3><b>Статус:</b> ${parkin.listValues?api.get(308?long)}</h3>
                    </div>
                </div>
            </#if>
        </#list>
        </div>
    </div>
</div>
</body>
</html>