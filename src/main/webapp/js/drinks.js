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

/**
 * Создает элемент с информацией о напитке
 * @param drink напиток
 */
function getDrinkView(drink) {
    let div = document.createElement("div");
    let item = document.createElement("div");

    let name = getTextDiv(drink.name);
    name.style.cssText = 'font-size:160%;text-align:center;font-family:verdana;'
    item.appendChild(name);
    item.appendChild(getTextDiv(drink.volume + " мл"));
    item.appendChild(getTextDiv(drink.price + " руб"));

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

function onDrinksLoad(response) {
    if (response.ok) {
        return response.json();
    } else {
        //TODO: сказать об ошибке
    }
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

function renderDrinks(page) {
    let row = document.getElementById("drinks");
    row.innerHTML = "";
    currentPage = page;
    setPagination(page);
    page.content.forEach(drink => row.appendChild(getDrinkView(drink)))
}


function getCategoryButton(category) {
    let button = document.createElement("label");
    button.innerHTML=category.name;
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

function getToppingRadios(topp) {

}