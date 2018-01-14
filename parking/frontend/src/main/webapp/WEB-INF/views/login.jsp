<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html class="no-js" lang="ru">

<head>

    <meta charset="utf-8">

    <title>Заголовок</title>
    <meta name="description" content="">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <meta property="og:image" content="path/to/image.jpg">

    <link rel="shortcut icon" href="/resources/img/favicon/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon" href="/resources/img/favicon/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/resources/img/favicon/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/resources/img/favicon/apple-touch-icon-114x114.png">

</head>

<body>
<div class="main" style="background-image: url(img/fon2.jpg);">
    <div class="regwrapper z-depth-3">
        <div class="row">
            <div class="col s5">
                <p>Название$ </p>
                <p>Ченьть про сервис</p>
                <p>Мб лого</p>
            </div>
            <div class="regform col s7">
                <ul id="singtabs" class="tabs">
                    <li class="tab col s3"><a class="active" href="#singin">Вход</a></li>
                    <li class="tab col s5"><a href="#singup">Регистрация</a></li>
                </ul>
                </br>
                <div class="tabswrap">
                    <div id="singin" class="">
                        <div class="row">
                            <form:form method="POST" commandName="user" action="check-user">
                                <div class="input-field col s12">
                                    <form:input path="name"></form:input> <!--id="icon_prefix" type="text" class="validate" />-->
                                    <label for="icon_prefix">Логин</label>
                                </div>
                                <div class="input-field col s12">
                                    <form:input path="password" id="icon_pass" type="password" class="validate" />
                                    <label for="icon_pass">Пароль</label>
                                </div>
                                <p class="col s12">
                                    <input type="checkbox" id="test6" checked="checked" />
                                    <label for="test6">Оставаться в системе</label>
                                </p>
                                <button class="btn waves-effect waves-light" type="submit" name="action">
                                    Войти
                                </button>
                                <div class="clearfix"></div>
                            </form:form>
                            <div class="singinsoc">
                                <span class="block center-align">- Войти через -</span>
                                <div class="col s4"><a href="#" class="btn-flat center-align btnbord">vk</a></div>
                                <div class="col s4"><a href="#" class="btn-flat center-align btnbord">Google</a></div>
                                <div class="col s4"><a href="#" class="btn-flat center-align btnbord">Facebook</a></div>
                            </div>
                        </div>
                    </div>
                    <div id="singup">
                        <div class="row">
                            <form>
                                <div class="input-field col s12">
                                    <input id="first_name" type="text" class="validate" required>
                                    <label for="first_name">ФИО</label>
                                </div>
                                <div class="input-field col s12">
                                    <input id="telephone" type="tel" class="validate" pattern="\d{11}" required>
                                    <label for="telephone" data-error="Чет не то!   Пример: 89601234567" data-success="">Телефон</label>
                                </div>
                                <div class="input-field col s12">
                                    <input id="email" type="email" class="validate" required>
                                    <label for="email" data-error="Чет не то!   Пример: qwer@mail.ru" data-success="">Email</label>
                                </div>
                                <div class="input-field inline col s6" required>
                                    <input id="password1" type="password" class="validate">
                                    <label for="password">Пароль</label>
                                </div>
                                <div class="input-field inline col s6" required>
                                    <input id="password2" type="password" class="validate">
                                    <label for="password">Еще раз</label>
                                </div>
                                <p class="inline col s6">
                                    <input class="with-gap" name="group1" type="radio" id="role1" checked/>
                                    <label for="role1">Арендатор</label>
                                </p>
                                <p class="inline col s6">
                                    <input class="with-gap" name="group1" type="radio" id="role2"/>
                                    <label for="role2">Арендодатель</label>
                                </p>
                                <button class="btn waves-effect waves-light" type="submit" name="action">
                                    Зарегистрироваться
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<link rel="stylesheet" href="/resources/css/fonts.min.css">
<link rel="stylesheet" href="/resources/css/main.min.css">
<link rel="stylesheet" href="/resources/libs/materialize/css/materialize.min.css">
<script src="/resources/js/libs.js"></script>
<script src="/resources/js/common.js"></script>
<script src="/resources/libs/materialize/js/materialize.min.js"></script>

</body>
</html>
