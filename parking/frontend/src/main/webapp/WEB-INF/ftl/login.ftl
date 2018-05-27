<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring/>
<!doctype html>
<head>
    <meta charset="utf-8" />
    <link href="http://yastatic.net/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://yastatic.net/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://yastatic.net/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <#include "css/login_styles.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход/Регистрация</title>
</head>
<body>

<div class="parent">
    <div class="tab" role="tabpanel">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <#if regError??>
                <li role="presentation"><a href="#Section1" aria-controls="home" role="tab" data-toggle="tab">Вход</a></li>
                <li role="presentation" class="active"><a href="#Section2" aria-controls="profile" role="tab" data-toggle="tab">Регистрация</a></li>
            <#else>
                <li role="presentation" class="active"><a href="#Section1" aria-controls="home" role="tab" data-toggle="tab">Вход</a></li>
                <li role="presentation"><a href="#Section2" aria-controls="profile" role="tab" data-toggle="tab">Регистрация</a></li>
            </#if>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content tabs">
            <!-- Selection 1 -->
            <#if regerror??>
                <div role="tabpanel" class="tab-pane fade" id="Section1">
            <#else>
                <div role="tabpanel" class="tab-pane fade in active" id="Section1">
            </#if>
                <form class="form-horizontal" action="j_spring_security_check" method="post">
                    <div class="form-group">
                        <label for="exampleInputEmail1">Телефон / Email</label>
                        <input type="email" class="form-control" id="exampleInputEmail1" name="j_username">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Пароль</label>
                        <input type="password" class="form-control" id="exampleInputPassword1" name="j_password">
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-default">Войти</button>
                        <div class="login-form-error">${error!}</div>
                        <div class="login-form-message">${message!}</div>
                    </div>
                </form>
            </div>
            <!-- Selection 2 -->
            <#if regerror??>
                <div role="tabpanel" class="tab-pane fade in active" id="Section2">
            <#else>
                <div role="tabpanel" class="tab-pane fade" id="Section2">
            </#if>
                <form class="form-horizontal" action="/register" method="post" >
                    <div class="form-group">
                        <label for="inputName">Имя</label>
                        <@spring.formInput "obj.name", "class='form-control'", "text"/>
                    </div>
                    <div class="form-group">
                        <label for="inputPhone">Номер телефона</label>
                        <@spring.formInput "obj.values['${201?long}']", "class='form-control bfh-phone' data-format='+7 (ddd) ddd-dddd'", "text"/>
                    </div>
                    <div class="form-group">
                        <label for="inputEmail">E-mail</label>
                        <@spring.formInput "obj.values['${202?long}']", "class='form-control'", "email"/>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">Пароль</label>
                        <@spring.formInput "obj.values['${203?long}']", "class='form-control'", "password"/>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-default">Зарегистрироваться</button>
                    </div>
                    <div class="login-form-error">${regError!}</div>
                </form>
            </div>
        </div>
    </div>
</div>
<#--<script src="js/bootstrap-formhelpers-phone.js"></script>-->
<#--</@security.authorize>-->
</body>
</html>