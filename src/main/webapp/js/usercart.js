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

function onOrderPlace() {
    event.preventDefault();
    let drinkDiv = document.getElementsByName('drinks')[0];
    let id = drinkDiv.dataset["id"];
    let url = "/api/order/" + id + "/place";
    return fetch(url, {method: "PUT"})
        .then(function (response) {
            if (response.ok) {
                let title = document.getElementById("modal-title");
                title.innerHTML = "Размещение заказа";

                let body = document.getElementById("modal-body");
                body.innerHTML = "Ваш заказ размещен";

                $('#overlay').modal('show');

                setTimeout(function() {
                    $('#overlay').modal('hide');
                }, 5000);
                setTimeout(function () {
                    window.location = window.location.origin + "/cart";
                }, 5000);
            } else if (response.status === 500) {
                alert("Ошибка сервера, повторите запрос позже");
            }
        });
}

function onDrinkInOrderDelete(drinkInOrderId) {
    event.preventDefault();
    let url = "api/cart/" + drinkInOrderId;
    return fetch(url, {method: "DELETE"})
        .then(function (response) {
            if (response.ok) {
                window.location = window.location.origin + "/cart";
            } else if (response.status === 500) {
                alert("Ошибка сервера, повторите запрос позже");
            }
        } )
}

function renderCart(cart) {
    let root = document.getElementById("cart");
    if (cart["drinks"].length === 0) {
        root.innerHTML = "Здесь пока ничего нет";
    } else {

        let drinks = document.createElement("div");
        drinks.setAttribute("name", "drinks");
        drinks.setAttribute('data-id', cart.id);

        let ul = document.createElement("ul");

        cart["drinks"]
            .forEach(drinkInOrder => ul.appendChild(renderDrinkInOrder(drinkInOrder)));

        drinks.appendChild(ul);

        drinks.appendChild(getTextDiv("Итоговая стоимость : " + cart.total));
        root.appendChild(drinks);

        let placeOrderButton = getPlaceOrderButton("Разместить заказ ");
        placeOrderButton.onclick = () => onOrderPlace();

        root.appendChild(placeOrderButton);

    }
}

function getPlaceOrderButton(text) {

    let div = document.createElement("div");
    let button = document.createElement("button");
    let span = document.createElement("span");

    button.type = "button";
    button.classList.add("btn");
    button.classList.add("btn-primary");
    button.style.cssText = 'text-align:center;font-family:courier;';
    button.innerHTML = text;

    div.style.cssText = 'text-align:center;font-family:courier;';

    span.classList.add("cui-basket-loaded");

    button.appendChild(span);
    div.appendChild(button);

    return div;
}

function getTextDiv(text) {
    let div = document.createElement("div");
    div.innerHTML = text;
    return div;
}

function renderDrinkInOrder(drinkInOrder) {
    let li = document.createElement("li");
    let div = document.createElement("div");

    let span = document.createElement("span");
    span.classList.add("cui-trash");
    span.onclick = () => onDrinkInOrderDelete(drinkInOrder.id);

    let nameDiv = getTextDiv(drinkInOrder.drink.name);
    nameDiv.setAttribute("data-id", drinkInOrder.id);
    nameDiv.id = "drink-in-order-" + drinkInOrder.id;
    nameDiv.appendChild(span);

    let toppingsList = document.createElement("ul");
    drinkInOrder.toppings.forEach(toppingInOrder => {
        let li = document.createElement("li");
        li.innerHTML = toppingInOrder.topping.name;

        toppingsList.appendChild(li);
    });

    div.appendChild(nameDiv);
    div.appendChild(toppingsList);
    li.appendChild(div);
    return li;
}
