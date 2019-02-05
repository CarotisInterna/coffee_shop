<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <title>Логин</title>
</head>
<body class="masthead">
<div class="container">
    <form name="f" action="login" method="post">
        <fieldset>
            <legend>Войти</legend>
            <div class="form-group">
                <label for="username">Имя пользователя</label>
                <input type="text" id="username" name="username" value="${(user.username)!''}" required/>
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" id="password" name="password" value="${(user.password)!''}" required/>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn">Войти</button>
            </div>
            <#if RequestParameters["error"]??>
                <p>Имя пользователя или пароль неверны, попробуйте еще раз.</p>
            </#if>
        </fieldset>
    </form>
</div>
</body>
</html>