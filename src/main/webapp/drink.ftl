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
    <#if !drink??>
        Не найден напиток
    <#else>
        <div>
            <label>Название</label>
            <div>
            <input value="${(drink.name)!''}" required
                <#if drink.id??>
                   disabled
                </#if>
            </div>
        </div>
        <div>
            <label>Цена</label>
            <div>
                <input value="${(drink.price)!''}" required/>
            </div>
        </div>
        <div>
            <label>Объем</label>
            <div>
                <input value="${(drink.volume)!''}" required/>
            </div>
        </div>
        <div>
            <label>Описание</label>
            <textarea>
            ${(drink.description)!''}
        </textarea>
        </div>
        <div>
            <label>Категории</label>
            TODO: мультиселект как с топпингами на главной
            <select>
                <#list categories as category>
                    <option value="${category.id}">${category.name}</option>
                </#list>
            </select>
        </div>
        <div>
            <button>Сохранить</button>
        </div>
    </#if>
</div>
</body>
</html>