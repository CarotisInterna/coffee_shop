<html lang="en">
<head>
    <title>Напитки</title>
    <#include 'head.ftl'/>
    <script src="./js/drinks.js"></script>
    <script src="./js/pagination.js"></script>
</head>
<body>
<div class="container">
<#include 'navbar.ftl'/>
    <div class="container" style="text-align:center;">
        <h2>Покупай</h2>
    </div>
    <div class="container" style="margin-top: 5%; margin-bottom: 5%;">
        <div id="categories_buttons" class="btn-group btn-group-toggle" data-toggle="buttons">
        <#--<a class="btn btn-outline-primary" href="#">-->
                <#--ffffffff-->
            <#--</a>-->
            <#--<a class="btn btn-outline-primary" href="#">-->
                <#--aaaaaaaa-->
            <#--</a>-->
            <#--<a class="btn btn-outline-primary" href="#">-->
                <#--bbbbbbbb-->
            <#--</a>-->
        </div>
    </div>
    <div id="drinks" class="row">
    </div>
    <#include 'pagination.ftl'/>
</div>
</body>
</html>