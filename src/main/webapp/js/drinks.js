var currentPage = null;
var functionForGet = getDrinks;

document.addEventListener("DOMContentLoaded", () => {
    getCategories();
    document.getElementById('next-page').onclick = nextDrinkPage;
    document.getElementById('prev-page').onclick = prevDrinkPage;
    getDrinks();
});

function getDrinks() {
    fetchProducts(currentPage)
        .then(onDrinksLoad)
        .then(renderDrinks)
        .catch(function (error) {
            console.log(error);
            //TODO: показать алерт с сообщением
        });
    functionForGet = getDrinks;
}


function nextDrinkPage() {
    currentPage.number += 1;
    functionForGet();
}

function prevDrinkPage() {
    currentPage.number -= 1;
    functionForGet();
}

function fetchProducts(page, search) {
    let s = "/api/drinks/search";
    let params = new URLSearchParams();
    if (page) {
        if (page.number !== null) params.append('page', page.number);
        if (page.size !== null) params.append('size', page.size);

    }
    if (search) {
        //TODO: more search params to use
        if (search.category_id !== null) params.append("category_id", search.category_id)
    }
    s += "?" + params.toString();
    return fetch(s);
}

function getTextDiv(text) {
    let div = document.createElement("div");
    let p = document.createElement("p");
    p.innerHTML = text;
    div.appendChild(p);
    div.style.cssText = 'text-align:center;font-family:courier;'
    return div;
}

function getDrinkImg(image) {
    let img = document.createElement("img");
    img.setAttribute("width", "200");
    img.setAttribute("height", "200");
    img.setAttribute("src", "../api/images/" + image);
    let div = document.createElement("div");
    div.appendChild(img);
    div.style.cssText = 'text-align:center;margin-top:10%;';
    return div;
}

/**
 * Создает элемент с информацией о напитке
 * @param drink напиток
 */
function getDrinkView(drink) {
    let div = document.createElement("div");
    let item = document.createElement("div");

    let image = getDrinkImg(drink.images[0]);

    let name = getTextDiv(drink.name);
    name.style.cssText = 'font-size:160%;text-align:center;font-family:verdana;'

    item.appendChild(image);
    item.appendChild(name);
    item.appendChild(getTextDiv(drink.volume + " мл"));
    item.appendChild(getTextDiv(drink.price + " руб"));

    let ul = document.createElement("ul");
    ul.classList.add("options");
    item.appendChild(ul);

    getToppingSelect(item, ul);

    div.appendChild(item);
    div.classList.add("col-4");
    div.classList.add("border");
    div.classList.add("border-primary");
    return div;
}

function fetchCategories() {
    let s = "/api/categories";
    return fetch(s);
}

function fetchToppings() {
    let s = "/api/toppings";
    return fetch(s);
}

function onDrinksLoad(response) {
    if (response.ok) {
        return response.json();
    } else {
        //TODO: сказать об ошибке
    }
}

function selectTopping(select, ul) {

    var option = select.options[select.selectedIndex];

    var choices = ul.getElementsByTagName('li');
    for (var i = 0; i < choices.length; i++)
        if (choices[i].id === option.value)
            return;

    var li = document.createElement('li');

    li.style.cssText = 'list-style-type:none;font-size:80%;font-family:courier';
    li.id = option.value;

    var span = document.createElement("span");

    span.classList.add("cui-circle-x");
    span.style.cssText = 'text-align:right;font-size:160%';
    li.innerHTML = option.firstChild.data;
    li.appendChild(span);

    li.name = 'toppings[]';
    li.value = option.value;

    li.setAttribute('onclick', 'this.parentNode.removeChild(this);');

    ul.appendChild(li);
}


function getDrinksByCategory(id, page) {
    fetchProducts(page, {"category_id": id})
        .then(onDrinksLoad)
        .then(renderDrinks)
        .catch(function (error) {
            console.log(error);
            //TODO: показать алерт с сообщением
        });
    //TODO: может быть баг
    functionForGet = () => getDrinksByCategory(id, currentPage);
}

function getDrinksByName(name, page) {
    fetchProducts(page, {"name": name})
        .then(onDrinksLoad)
        .then(renderDrinks)
        .catch(function (error) {
            console.log(error);
            //TODO: показать алерт с сообщением
        });
    //TODO: может быть баг
    functionForGet = () => getDrinksByName(name, currentPage);
}

function renderDrinks(page) {
    let row = document.getElementById("drinks");
    row.innerHTML = "";
    currentPage = page;
    setPagination(page);
    page.content.forEach(drink => row.appendChild(getDrinkView(drink)))
}

function search() {
    let input = document.getElementById('search')
    getDrinksByName(input.value)
}

function getCategoryButton(category) {
    let button = document.createElement("label");
    button.innerHTML = category.name;
    button.classList.add("btn");
    button.classList.add("btn-outline-primary");
    let input = document.createElement("input");
    input.type = "radio";
    input.id = category.id;
    input.value = category.name;
    button.appendChild(input);
    // button.classList.add("btn");
    // button.classList.add("btn-outline-primary");
    button.onclick = () => getDrinksByCategory(category.id);
    return button;
}

function getToppingOption(topping) {
    let option = document.createElement("option");
    option.value = topping.name;
    option.innerHTML = topping.name + " " + topping.price + " руб.";
    return option;
}

function getCategories() {
    fetchCategories()
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                //TODO: сказать об ошибке
            }
        })
        .then(list => {
            let buttons = document.getElementById("categories_buttons");
            list.forEach(el => buttons.appendChild(getCategoryButton(el)))
        })
        .catch(error => console.log(error));
}

function getToppingSelect(item, ul) {
    fetchToppings()
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                //TODO: сказать об ошибке
            }
        })
        .then(list => {
            let div = document.createElement("div");
            div.id = "topping-select"
            div.style.cssText = 'text-align:center';
            let select = document.createElement("select");
            select.classList.add("options");
            select.style.cssText = 'border-radius:10px;text-align:center;font-family:courier;margin-bottom:10%;font-size:80%;';
            select.required = true;
            div.appendChild(select);
            let option = document.createElement("option");
            option.value = "";
            option.disabled = true;
            option.selected = true;
            option.innerHTML = "Добавить топпинг";
            select.appendChild(option);
            list.forEach(el => select.appendChild(getToppingOption(el)));
            select.onchange = () => selectTopping(select, ul);
            item.appendChild(div)
        })
        .catch(error => console.log(error));
}