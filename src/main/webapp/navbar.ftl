<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
    <a class="navbar-brand" href="#">
        <img src="<@spring.url '/api/images/icon'/>" width="30" height="30" class="d-inline-block align-top" alt="">
        Coffee Shop
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <div class="form-inline my-1 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Найти" aria-label="Search" id="search">
                    <button class="btn btn-outline-primary my-2 my-sm-0" onclick="search()" id="search-button">Найти</button>
                </div>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="<@spring.url '/drinks'/>">Меню <span class="sr-only">(current)</span></a>
            </li>
            <@security.authorize  access="hasRole('ROLE_VENDOR')">
                <li class="nav-item dropdown" id="edit">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        Редактировать
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="/edit/drinks">Напиток</a>
                        <a class="dropdown-item" href="/edit/toppings">Топпинг</a>
                    </div>
                </li>
                <li class="nav-item dropdown" id="create">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        Создать
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="/create/drinks">Напиток</a>
                        <a class="dropdown-item" href="/create/toppings">Топпинг</a>
                        <#--<div class="dropdown-divider"></div>-->
                        <#--<a class="dropdown-item" href="#">Something else here</a>-->
                    </div>
                </li>
            </@security.authorize>
        </ul>
        <ul class="navbar-nav ml-auto">
            <@security.authorize  access="isAuthenticated()">
                <span class="navbar-text"> Привет, <@security.authentication property='principal.username'/> !</span>
                <li class="nav-item" type="none">
                    <a class="nav-link btn btn-ghost-secondary my-2 my-sm-0" href="<@spring.url '/logout'/>">
                        <span class="cui-account-logout"></span>
                        Выход
                    </a>
                </li>
            </@security.authorize>
            <@security.authorize  access="isAnonymous()">
                <li class="nav-item" type="none">
                    <a class="nav-link btn btn-ghost-secondary my-2 my-sm-0" href="<@spring.url '/login'/>"">
                        <span class="cui-user"></span>
                        Войти
                    </a>
                </li>
            </@security.authorize>
        </ul>
    </div>
</nav>