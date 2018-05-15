<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<html>
<head>
    <title>Тестовая страница</title>
    <meta charset="utf-8" />
    <link href="http://yastatic.net/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://yastatic.net/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://yastatic.net/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <#include "css/404_styles.css">
</head>
<body>
<div class="inf">
    <div class="col-lg-6 col-md-12 text-info">
        <h1>Oooops...</h1>
        <h2>
            <b>Нам очень жаль, но такой страницы не существует.
                <br>Возможно вы ошиблись или информация еше
                <br>  не добавлена.</b>
        </h2>
        <h2><b>Некоторые ссылки, которые возможно помогут вам:</b></h2>
        <ul>
            <li><a href="/frontend/index">Главная</a></li>
            <li><a href="">Техническая поддержка</a></li>
            <li><a href="">О нас</a></li>
            <li><a href="">Контакты</a></li>
        </ul>
    </div>
    <div class="col-lg-6 col-md-12 image">
        <img src="" />
    </div>
</div>
</body>
</html>