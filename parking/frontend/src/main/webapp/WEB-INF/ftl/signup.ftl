<!DOCTYPE html>
<head>
    <#--<meta charset="UTF-8">-->
    <title>Регистрация пользователя</title>

</head>
<body>

<table>

<form name="user" action="/signup" method="post">
    <p>Имя</p>
    <input title="Имя" type="text" name="name" value="${obj.name}">
    <input type="submit" value="Создать">
</form>
                                <#assign key_list = obj.values?keys />
                            <#assign value_list = obj.values?values />
                            <#assign seq_index = key_list?seq_index_of(202?long) />
                            <#assign value = value_list[seq_index] />
    value = ${value}
                            <#assign v = obj.values?api.get(202?long) />
    v = ${v}
<#--<@spring.formInput path="obj.values['${202?long}']"/>-->
                            <@spring.formInput "obj.values['${202?long}']", "class='form-control'", "email"/>
</body>
</html>

