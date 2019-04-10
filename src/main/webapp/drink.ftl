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
              <#if drink.id??>data-id="${drink.id}"</#if>>
            <div id="name-div" class="form-group">
                <label for="name" class="control-label col-sm-2">Название</label>
                <div class="col-sm-10">
                    <input id="name" name="name" value="${(drink.name)!''}" required
                        <#if drink.id??>
                           readonly
                    </#if>
                </div>
            </div>

            <div id="price-div" class="form-group">
                <label for="price" class="control-label col-sm-2">Цена в российских рублях</label>
                <div class="col-sm-10">
                    <input type="number" id="price" name="price" value="${(drink.price)!''}" required/>
                </div>
            </div>
            <div id="volume-div" class="form-group">
                <label for="volume" class="control-label col-sm-2">Объем в миллилитрах</label>
                <div class="col-sm-10">
                    <input type="number" id="volume" name="volume" value="${(drink.volume)!''}" required/>
                </div>
            </div>
            <div id="description-div" class="form-group">
                <label for="description" class="control-label col-sm-2">Описание</label>
                <div class="col-sm-10">
                    <textarea id="description" name="description">${(drink.description)!''}</textarea>
                </div>
            </div>
            <div id="categories-div" class="form-group">
                <label for="categories">Категории</label>
                <div class="col-sm-10">
                    <select id="categories" name="categories" multiple>
                        <#list categories as category>
                            <option value="${category.id}"
                                    <#if drink.id??>
                                        ${drink.categories?seq_contains(category.name)?string("selected", "")}
                                    </#if>
                            >${category.name}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div id="picture-div" class="form-group">
                Изображение
                <br>
                <#if drink.images?has_content>
                    <#list drink.images as image>
                        <div class="form-check form-check-inline">
                            <img src="<@spring.url '/api/images/' + image/>"/>
                        </div>
                    </#list>
                </#if>
                <#if !drink.id??>
                    <div class="col-sm-10">
                        <div id="photos" class="row">
                            <div class="col-sm-6 btn-group">
                                <input class="btn" type="file" name="images" <#if !drink.id??>required</#if>/>
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
            <div>
                <button type="submit" onclick="
                <#if drink.id??>
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