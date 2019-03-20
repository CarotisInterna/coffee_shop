<html lang="en">
<head>
    <title>${message}</title>
<#include 'head.ftl'/>
</head>
<body>
<#include 'navbar.ftl'/>
<div class="container" style="text-align:center;">
    <h2>${message}</h2>
</div>
<div>
    <#if !topping??>
        Не найден топпинг
    <#else>
        <div>
            <label>Название</label>
            <div>
            <input value="${(topping.name)!''}" required
                <#if topping.id??>
                   disabled
                </#if>
            </div>
        </div>
        <div>
            <label>Цена</label>
            <div>
                <input value="${(topping.price)!''}" required/>
            </div>
        </div>
        </div>
        <div>
            <button>Сохранить</button>
        </div>
    </#if>
</div>
</body>
</html>