<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <title>${message}</title>
<#include 'head.ftl'/>
    <script src="<@spring.url '/js/drink.js'/>"></script>
</head>
<body>
<#include 'navbar.ftl'/>
<div class="container" style="text-align:center;">
    <h2>${message}</h2>
</div>
<div class="container">
<#if !drink??>
    Не найден напиток
<#else>
    <form id="create-drink" enctype="multipart/form-data" name="drink" class="form-horizontal"
          onsubmit="onCreate(this)">
        <div class="form-group">
            <label for="name" class="control-label col-sm-2">Название</label>
            <div class="col-sm-10">
            <input id="name" name="name" value="${(drink.name)!''}" required
                <#if drink.id??>
                   disabled
                </#if>
            </div>
        </div>

        <div class="form-group">
            <label for="price" class="control-label col-sm-2">Цена</label>
            <div class="col-sm-10">
                <input id="price" name="price" value="${(drink.price)!''}" required/>
            </div>
        </div>
        <div class="form-group">
            <label for="volume" class="control-label col-sm-2">Объем</label>
            <div class="col-sm-10">
                <input id="volume" name="volume" value="${(drink.volume)!''}" required/>
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="control-label col-sm-2">Описание</label>
            <div class="col-sm-10">
                <textarea id="description" name="description">${(drink.description)!''}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="categories">Категории</label>
            <div class="col-sm-10">
                <select id="categories" name="categories" multiple>
                    <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="file" class="control-label col-sm-2"> Изображение </label>
            <div class="col-sm-10">
                <div id="photos" class="row">
                    <div class="col-sm-6 btn-group">
                        <input class="btn" type="file" name="images" required data-file-id="1"/>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <button type="submit">Сохранить</button>
        </div>
    </form>
</#if>
</div>
</body>
</html>