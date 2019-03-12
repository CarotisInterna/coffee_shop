var currentPage = {"number": 0, "size": 9};

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById('next-page').onclick = nextDrinkPage;
    document.getElementById('prev-page').onclick = prevDrinkPage;
    getDrinks();
});

function getDrinks() {
    fetchProducts(currentPage)
        .then(function (response) {
            if (response.ok) {
                return response.json();
            } else {
                //TODO: сказать об ошибке
            }
        }).then(function (page) {
        let row = document.getElementById("drinks");
        row.innerHTML = "";
        currentPage = page;
        setPagination(page);
        page.content.forEach(drink => row.appendChild(getDrinkView(drink)))
    }).catch(function (error) {
        console.log(error);
        //TODO: показать алерт с сообщением
    })
}


function nextDrinkPage() {
    currentPage.number += 1;
    getDrinks();
}

function prevDrinkPage() {
    currentPage.number -= 1;
    getDrinks();
}

function fetchProducts(page) {
    let s = "/api/drinks/search";
    if (page === null || page.number === null) page = 0;
    if (page === null || page.size === null) size = 9;
    let params = new URLSearchParams();
    params.append('page', page.number);
    params.append('size', page.size);
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

    let name = getTextDiv(drink.name)
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

function getToppingRadios(topp) {

}