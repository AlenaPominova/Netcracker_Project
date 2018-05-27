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
    <script src='https://www.google.com/recaptcha/api.js'></script>
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
            <a class="navbar-brand" href="${mainPageUrl}"><img src="https://i.imgur.com/oH893fM.png" alt="Воронежский паркинг"></a>
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
                            <a href="${mainPageUrl}/profiles/${currentUserId!}">
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
                <h3><b>Овертайм:</b> ${parking.listValues?api.get(309?long)!'отсутствует'}</h3>
                <#if parking.listValues?api.get(309?long)??>
                    <#if parking.listValues?api.get(309?long) == "Повышенние стоимости аренды">
                        <h3><b>Коэффициент:</b> х${parking.values?api.get(310?long)}</h3>
                    </#if>
                </#if>
                <#if parking.listValues?api.get(308?long) == "Occupied">
                    <h3><b>Статус:</b> Занято </h3>
                <#else >
                    <h3><b>Статус:</b> Свободно </h3>
                </#if>
                <a href="${mainPageUrl}/profiles/${parking.references?api.get(300?long)}">Страница владельца</a>
            </div>
        </div>
        <#if !(success?? || (parking.values?api.get(307?long) == "0") || (parking.values?api.get(307?long) != "1")) >
            <div class="col-lg-7 col-md-7" style="padding-left: 0px; padding-right: 0px; padding-top: 0px;">
                <div class="col-lg-12 col-md-12">
                    <div class="span4">
                        <div class="panel panel-danger">
                            <div class="panel-heading">Соглашение об аренде парковочного места</div>
                            <div class="panel-body panel-height">
                                <div class="rules">
                                    <h4>ПРАВА И ОБЯЗАННОСТИ СТОРОН</h4>
                                    <ol>
                                        <li>Арендодатель вправе: в любое время осуществлять контроль за обеспечением имущественной сохранности Парковочного места;</li>
                                        <li>Арендодатель вправе: при наличии задолженности у Арендатора по оплате арендных платежей более суток (24 часа) без предупреждения изъять Автомобиль у Арендатора, объявить Автомобиль в розыск, дистанционно заглушить двигатель, а также прибегнуть к иным защитным мерам;</li>
                                        <li>Арендодатель вправе: в случае наличия достаточных оснований полагать о возможности совершения мошеннических действий, а также иных проявлениях недобросовестного поведения со стороны Арендатора, прекратить исполнение по данному Договору, в одностороннем порядке уведомив Арендатора;</li>
                                        <li>Арендодатель вправе: в случае отсутствия ответа со стороны Арендатора на звонки и смс, переместить автомобиль с парковочного места;</li>
                                        <li>Арендодатель обязуется: предоставить Арендатору Парковочное место в технически исправном состоянии, отвечающем требованиям эксплуатации Парковочного места;</li>
                                        <li>Арендатор обязуется: принять в соответствии с условиями настоящего Договора и бережно использовать Парковочное место в строгом соответствии с его назначением и целями, указанными в настоящем Договоре, своевременно принимать меры по недопущению и предотвращению ущерба имуществу Арендодателя, ликвидации соответствующих последствий;</li>
                                        <li>Арендатор обязуется: своевременно, в соответствии с условиями настоящего Договора вносить арендную плату, оплачивать иные платежи, предусмотренные Договором;</li>
                                        <li>Арендатор обязуется: по окончании аренды Парковочного места возвратить его Арендодателю в надлежащем техническом состоянии в порядке, предусмотренном настоящим Договором;</li>
                                        <li>Арендатор обязуется: в случае получения от Арендодателя уведомления об перемещении Автомобиля в период аренды (или по окончании аренды, в случае если перемещение осуществляется по причине нарушения правил аренды Арендатором) в течение 12 часов с момента получения уведомления осуществить все необходимые действия по возврату Автомобиля со стоянки. Все расходы, связанные с перемещением Автомобиля, в полном объеме несет Арендатор, такие расходы не компенсируются Арендодателем;</li>
                                        <li>Арендатор вправе: арендовать в разное время различные Парковочные места;</li>
                                        <li>Арендатор вправе: арендовать у Арендодателя любое Парковочное место, не находящееся в аренде на момент бронирования;</li>
                                        <li>Указанные условия Стороны договорились считать существенными условиями Договора.</li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12 col-md-12">
                    <#--<form method="get" action="/parkings/${parking.id}/rent">-->
                        <#--<div class="form-group">-->
                            <#--<div class="main-checkbox">-->
                                <#--<input value="confirmed" id="checkbox1" name="status" type="checkbox">-->
                                <#--<label for="checkbox1"></label>-->
                            <#--</div>-->
                            <#--<span class="text">С правилами об аренде парковочного места ознакомлен и принимаю их.</span>-->
                        <#--</div>-->
                        <#--<button type="submit" class="btn-chg">АРЕНДОВАТЬ</button>-->
                    <#--</form>-->
                    <form method="post" action="/parkings/${parking.id}/rent">
                        <div class="form-group">
                            <div class="main-checkbox">
                                <input value="confirmed" id="checkbox1" name="status" type="checkbox">
                                <label for="checkbox1"></label>
                                <br><br><br><br><br><br>
                                <div class="g-recaptcha" data-sitekey="6Lfqp1sUAAAAAPUCtlILo-XUBl6ezI-aqP8yPPew"></div>
                            </div>
                            <span class="text">С правилами об аренде парковочного места ознакомлен и принимаю их.</span>
                        </div>
                        <button type="submit" class="btn-chg">АРЕНДОВАТЬ</button>
                        <#if error??>
                            <h4>${error} Try again!</h4>
                        </#if>
                    </form>
                </div>
            </div>
        <#else>
            <form method="get" action="${mainPageUrl}">
                <h4>${success}</h4>
                <button type="submit" class="btn-chg">ВЕРНУТЬСЯ НА ГЛАВНУЮ</button>
            </form>
        </#if>
    </div>
</div>
</body>
</html>