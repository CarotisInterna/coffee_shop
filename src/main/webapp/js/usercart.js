document.addEventListener("DOMContentLoaded", () => {
    getCart();
});

function getCart() {
    fetchCart()
        .then(onCartLoad)
        .then(renderCart)
}

function fetchCart() {
    let s = "/api/cart";
    return fetch(s);
}

function onCartLoad(response) {
    if (response.ok) {
        return response.json();
    } else {
        //TODO: сказать об ошибке
    }
}

function renderCart(cart) {
    let root = document.getElementById("cart");
    if (cart["drinks"].length === 0) {
        root.innerHTML = "Здесь пока ничего нет";
    } else {

        let drinks = document.createElement("div");
        drinks.setAttribute("name", "drinks");

        let ul = document.createElement("ul");

        cart["drinks"]
            .forEach(drinkInOrder => ul.appendChild(renderDrinkInOrder(drinkInOrder)));

        drinks.appendChild(ul);

        drinks.appendChild(getTextDiv("Итоговая стоимость : " + cart.total));
        root.appendChild(drinks);
    }
}

function getTextDiv(text) {
    let div = document.createElement("div");
    div.innerHTML = text;
    return div;
}

function renderDrinkInOrder(drinkInOrder) {
    let li = document.createElement("li");
    let div = document.createElement("div");
    let nameDiv = getTextDiv(drinkInOrder.drink.name);
    let toppingsHeader = getTextDiv("Топпинги");
    let toppingsList = document.createElement("ul");
    drinkInOrder.toppings.forEach(toppingInOrder => {
        let li = document.createElement("li");
        li.innerHTML = toppingInOrder.topping.name;
        toppingsList.appendChild(li);
    });

    div.appendChild(nameDiv);
    div.appendChild(toppingsHeader);
    div.appendChild(toppingsList);
    li.appendChild(div);
    return li;
}
