<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring/>
<!doctype html>
<head>
    <title>Тестовая страница</title>
    <meta charset="utf-8" />
    <link href="http://yastatic.net/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://yastatic.net/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://yastatic.net/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <#include "css/login_styles.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <@security.authorize access="isAuthenticated()">
    logged in as
        <@security.authentication property="principal" />
        <@security.authentication property="authorities" />
    </@security.authorize>

<#--<@security.authorize access="! isAuthenticated()">-->
    Not logged in
    <div class="parent">
        <div class="tab" role="tabpanel">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#Section1" aria-controls="home" role="tab" data-toggle="tab">Вход</a></li>
                <li role="presentation"><a href="#Section2" aria-controls="profile" role="tab" data-toggle="tab">Регистрация</a></li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content tabs">
                <!-- Selection 1 -->
                <div role="tabpanel" class="tab-pane fade in active" id="Section1">
                    <form class="form-horizontal" action="j_spring_security_check" method="post">
                        <div class="form-group">
                            <label for="exampleInputEmail1">Телефон</label>
                            <input type="email" class="form-control" id="exampleInputEmail1" name="j_username">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Пароль</label>
                            <input type="password" class="form-control" id="exampleInputPassword1" name="j_password">
                        </div>
                        <div class="form-group">
                            <div class="main-checkbox">
                                <input value="None" id="checkbox1" name="check" type="checkbox">
                                <label for="checkbox1"></label>
                            </div>
                            <span class="text">Запомнить меня</span>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default">Войти</button>
                            <div class="login-form-error">${error!}</div>
                            <div class="login-form-message">${message!}</div>
                        </div>

                        <div class="form-group forgot-pass">
                            <button type="submit" class="btn btn-default">Забыли пароль</button>
                        </div>
                    </form>
                </div>
                <!-- Selection 2 -->
                <div role="tabpanel" class="tab-pane fade" id="Section2">
                    <form class="form-horizontal" action="/register" method="post" >
                        <div class="form-group">
                            <label for="inputName">Имя</label>
                            <#--<input type="text" class="form-control" id="inputName">-->
                            <#--<input type="text" class="form-control" id="inputName" name="name" value="${obj.name}">-->
                            <#--<@spring.formInput path="obj.values['${202?long}']"/>-->
                            <@spring.formInput "obj.name", "class='form-control'", "text"/>

                        </div>
                        <#--<div class="form-group">-->
                            <#--<label for="inputSurname">Фамилия</label>-->
                            <#--<input type="text" class="form-control" id="inputSurname">-->
                        <#--</div>-->
                        <div class="form-group">
                            <label for="inputPhone">Номер телефона</label>
                            <#--<input type="text" class="form-control bfh-phone" id="inputPhone" data-format="+7 (ddd) ddd-dddd">-->
                            <@spring.formInput "obj.values['${201?long}']", "class='form-control bfh-phone' data-format='+7 (ddd) ddd-dddd'", "text"/>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail">E-mail</label>
                            <#--<input type="email" class="form-control" id="inputEmail">-->
                            <@spring.formInput "obj.values['${202?long}']", "class='form-control'", "email"/>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword">Пароль</label>
                            <#--<input type="password" class="form-control" id="inputPassword">-->
                            <@spring.formInput "obj.values['${203?long}']", "class='form-control'", "password"/>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default">Зарегистрироваться</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <#--<script src="js/bootstrap-formhelpers-phone.js"></script>-->
<#--</@security.authorize>-->
</body>
</html>