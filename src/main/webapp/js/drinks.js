var currentPage = null;
var functionForGet = getDrinks;
var toppings = [];

document.addEventListener("DOMContentLoaded", () => {
    getCategories();
    getToppings();
    document.getElementById('next-page').onclick = nextDrinkPage;
    document.getElementById('prev-page').onclick = prevDrinkPage;
    getDrinks();
});

function getToppings() {
    fetchToppings()
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                //TODO: сказать об ошибке
            }
        }).then(list => {
        toppings = list;
    })
        .catch(error => console.log(error));
}

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
        if (search.name) params.append("name", search.name);
        if (search.category_id) params.append("category_id", search.category_id);
    }
    s += "?" + params.toString();
    return fetch(s);
}

function getTextDiv(text) {
    let div = document.createElement("div");
    let p = document.createElement("p");
    p.innerHTML = text;
    div.appendChild(p);
    div.style.cssText = 'text-align:center;font-family:courier;';
    return div;
}

function getButtonDiv(text) {
    let div = document.createElement("div");
    let button = document.createElement("button");
    button.type = "button";
    button.classList.add("btn");
    button.classList.add("btn-primary");
    button.style.cssText = 'text-align:center;font-size:90%;font-family:courier;';
    div.style.cssText = 'text-align:center;font-size:80%;font-family:courier;';
    button.innerHTML = text;
    div.appendChild(button);
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
    let drinkView = document.createElement("form");
    drinkView.setAttribute('data-id', drink.id);
    let item = document.createElement("div");

    let image = getDrinkImg(drink.images[0]);

    let name = getTextDiv(drink.name);
    name.style.cssText = 'font-size:160%;text-align:center;font-family:verdana;';
    name.classList.add("form-group");

    item.appendChild(image);
    item.appendChild(name);
    item.appendChild(getTextDiv(drink.volume + " мл"));
    item.appendChild(getTextDiv(drink.price + " руб"));
    let addToCartButton = getButtonDiv("Добавить в корзину");
    addToCartButton.onclick = () => addToCart(drinkView);
    item.appendChild(addToCartButton);

    let ul = document.createElement("ul");
    ul.classList.add("options");
    ul.setAttribute("name", "topping-select-" + drink.id);
    item.appendChild(ul);

    let toppingSelect = getToppingSelect(ul);

    item.appendChild(toppingSelect);

    drinkView.appendChild(item);
    drinkView.classList.add("col-4");
    drinkView.classList.add("border");
    drinkView.classList.add("border-primary");
    return drinkView;
}

function addToCart(form) {
    let drinkId = form.dataset["id"];
    let options = document.getElementsByName("topping-select-" + drinkId)[0];
    let toppings = [];
    for (let i = 0; i < options.children.length; i++) {
        let dataset = options.children.item(i).dataset["id"];
        toppings.push(dataset);
    }

    if (options !== null && options.children.length !== 0) {
        for (let i = options.children.length; i > 0; i--) {
            options.children.item(i-1).remove();
        }
    }

    fetchAddToCart(drinkId, toppings)

}

function fetchAddToCart(drinkId, toppings) {
    let params = new URLSearchParams();

    params.append('drinkId', drinkId);

    for (let i = 0; i < toppings.length; i++) {
        params.append("toppings", toppings[i]);
    }

    let url = "/api/cart?" + params.toString();

    return fetch(url, {method: "POST"})
        .then(function (response) {
        if (response.ok) {
            showModal("Добавление в корзину", "Продукт добавлен в корзину");
        } else if (response.status === 403) {
            window.location = window.location.origin + "/login";
            showModal("Добавление в корзину","Необходимо авторизоваться")
        }
    });
}

function showModal(titleMsg, message) {

    let title = document.getElementById("modal-title");
    title.innerHTML = titleMsg;

    let body = document.getElementById("modal-body");
    body.innerHTML = message;

    $('#overlay').modal('show');

    setTimeout(function() {
        $('#overlay').modal('hide');
    }, 2000);

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

    let option = select.options[select.selectedIndex];

    let choices = ul.getElementsByTagName('li');
    for (let i = 0; i < choices.length; i++)
        if (choices[i].id === option.value)
            return;

    let li = document.createElement('li');

    li.style.cssText = 'list-style-type:none;font-size:80%;font-family:courier';
    li.id = option.value;

    var span = document.createElement("span");

    span.classList.add("cui-circle-x");
    span.style.cssText = 'text-align:right;font-size:160%';
    li.innerHTML = option.firstChild.data;
    li.appendChild(span);

    li.name = 'toppings';
    li.dataset["id"] = option.value;

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
    let input = document.getElementById('search');
    getDrinksByName(input.value);
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
    button.onclick = () => getDrinksByCategory(category.id);
    return button;
}

function getToppingOption(topping) {
    let option = document.createElement("option");
    option.value = topping.id;
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

function getToppingSelect(ul) {
    let list = toppings;
    let div = document.createElement("div");
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
    return div;
}