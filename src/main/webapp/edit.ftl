<html lang="en">
<head>
    <title>${editMessage}</title>
<#include 'head.ftl'/>
</head>
<body>
<#include 'navbar.ftl'/>
<div class="container" style="text-align:center;">
    <h2>${editMessage}</h2>
</div>
<table class="table">
    <thead>
    <tr>
        <td>Название</td>
        <td></td>
    </tr>
    </thead>
    <tbody>
    <#list items as item>
    <tr>
        <td>
        ${item.name}
        </td>
        <td>
            <a class="btn btn-warning" href="${link}/${item.id}">Изменить</a>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
</body>
</html>