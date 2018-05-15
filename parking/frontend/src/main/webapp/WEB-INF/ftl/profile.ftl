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
            <a class="navbar-brand" href="#"><img src="https://i.imgur.com/oH893fM.png" alt="Воронежский паркинг"></a>
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
                            <a href="http://www.fgruber.ch/" target="_blank">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Личный кабинет</a>
                        </li>
                        <li>
                            <form method="get" action="${url}/logout">
                                <input type="submit" value="LOGOUT"/>
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
    <h1>Личный кабинет</h1>
    <div class="line"></div>
    <div class="row" style="padding-left: 15px; padding-right: 15px; padding-top: 50px;">
        <div class="col-lg-6" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
            <div class="col-lg-4" style="padding-left: 0px;">
                <img src="img/profile_photo.jpg" style="width: 200px; height: 200px; border-radius: 50px;">
            </div>
            <div class="col-lg-8 about-user" style="padding-top: 0px; margin-top: 0px;">
                <h2>Карпов Евгений</h2><p>( Владелец )</p>
                <h3 style="margin-top: 20px;"><b>Номер телефона:</b> +7 (999) 999 99 99</h3>
                <h3><b>E-mail:</b> believedream95@gmail.com</h3>
                <h3><b>Рейтинг:</b> 4,78</h3>
                <button class="btn-chg">РЕДАКТИРОВАТЬ</button>
            </div>
            <div class="col-lg-12 about-user" style="padding-left: 0px; margin-top: 30px;">
                <h2>История:</h2>
                <h3 style="margin-top: 20px;"><b>Сдал парковку:</b> 343</h3>
                <h3><b>Взял в аренду парковку:</b> 125</h3>
                <h3><b>На сервисе:</b> 300 дней</h3>
            </div>
        </div>
        <div class="col-lg-6" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
            <div class="col-lg-12 about-parking" style="padding-top: 0px; margin-top: 0px;">
                <h2>Список парковок:</h2>
            </div>
            <div class="col-lg-12">
                <div class="col-lg-5 parking-img">
                    <img src="" style="width: 200px; height: 150px; border-radius: 10px;">
                </div>
                <div class="col-lg-7 parking">
                    <h2>Парковка №256</h2>
                    <h3 style="margin-top: 5px;"><b>Стоимость:</b> 100 рублей/час</h3>
                    <h3><b>Свободные часы:</b> с 8:00 до 16:00</h3>
                    <button class="btn-chg">РЕДАКТИРОВАТЬ</button>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="col-lg-5 parking-img">
                    <img src="" style="width: 200px; height: 150px; border-radius: 10px;">
                </div>
                <div class="col-lg-7 parking">
                    <h2>Парковка №256</h2>
                    <h3 style="margin-top: 5px;"><b>Стоимость:</b> 100 рублей/час</h3>
                    <h3><b>Свободные часы:</b> с 8:00 до 16:00</h3>
                    <button class="btn-chg">РЕДАКТИРОВАТЬ</button>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="col-lg-5 parking-img">
                    <img src="" style="width: 200px; height: 150px; border-radius: 10px;">
                </div>
                <div class="col-lg-7 parking">
                    <h2>Парковка №256</h2>
                    <h3 style="margin-top: 5px;"><b>Стоимость:</b> 100 рублей/час</h3>
                    <h3><b>Свободные часы:</b> с 8:00 до 16:00</h3>
                    <button class="btn-chg">РЕДАКТИРОВАТЬ</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>