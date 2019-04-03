<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <title>Логин</title>
    <#include 'head.ftl'/>
    <#include 'navbar.ftl'/>
    <script src="./js/login.js"></script>
</head>
<body class="masthead">
<div class="container">
    <form id="login-form" name="f">
        <fieldset>
            <legend>Войти</legend>
            <div id="username-div" class="form-group">
                <label for="username">Имя пользователя</label>
                <input type="text" id="username" name="username" value="${(user.username)!''}" required/>
            </div>
            <div id="password-div" class="form-group">
                <label for="password">Пароль</label>
                <input type="password" id="password" name="password" value="${(user.password)!''}" required/>
            </div>
            <div class="form-actions">
                <button type="button" onclick="login()" class="btn">Войти</button>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>