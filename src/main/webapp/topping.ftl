<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <title>${message}</title>
    <#include 'head.ftl'/>
    <script src="<@spring.url '/js/topping.js'/>"></script>
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
        <form id="create-topping" name="topping" class="form-horizontal"
              <#if topping.id??>data-id="${topping.id}"</#if>>
            <div id="name-div" class="form-group">
                <label for="name" class="control-label col-sm-2">Название</label>
                <div class="col-sm-10">
                    <input id="name" name="name" value="${(topping.name)!''}" required
                        <#if topping.id??>
                           readonly
                    </#if>
                </div>
            </div>

            <div id="price-div" class="form-group">
                <label for="price" class="control-label col-sm-2">Цена в российских рублях</label>
                <div class="col-sm-10">
                    <input id="price" name="price" value="${(topping.price)!''}" required/>
                </div>
            </div>

            <div>
                <button type="submit" onclick="
                <#if topping.id??>
                        onEdit(event);
                <#else>
                        onCreate(event);
                </#if>">Сохранить
                </button>
            </div>
        </form>
    </#if>
</div>
</body>
</html>