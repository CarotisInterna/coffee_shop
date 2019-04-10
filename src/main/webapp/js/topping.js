function onCreate(event) {
    event.preventDefault();
    let data = new FormData(document.getElementById('create-topping'));
    sendForm('/api/toppings', 'POST', data);
}

function onEdit(event) {
    event.preventDefault();
    let form = document.getElementById('create-topping');
    let id = form.dataset["id"];
    let data = new FormData(form);
    sendForm('/api/toppings/' + id, 'PUT', data);
}

function sendForm(url, method, data) {

    var errorMsgs = document.getElementsByName("error");

    if (errorMsgs !== null && errorMsgs.length !== 0) {
        for (let i = errorMsgs.length - 1; i >= 0; i--) {
            errorMsgs[i].remove();
        }
    }

    let object = {};
    data.forEach((value, key) => {
        object[key] = value
    });
    let json = JSON.stringify(object);
    return fetch(url,
        {
            method: method,
            headers: {"Content-Type": "application/json"},
            body: json
        }).then(function (response) {
        if (response.ok) {
            window.location = window.location.origin + "/edit/toppings";
        } else if (response.status === 404 || response.status === 400) {
            return response.json();
        } else if (response.status === 500) {
            alert("Ошибка сервера, повторите запрос позже")
        }
    }).then(function (json) {
        if (json !== null) {
            for (let i = 0; i < json.errorDtos.length; i++) {
                let el = buildFieldErrorLabel(json.errorDtos[i].message);
                let div;
                if (json.errorDtos[i].field === "name") {
                    div = document.getElementById("name-div");
                } else if (json.errorDtos[i].field === "price") {
                    div = document.getElementById("price-div");
                }
                div.appendChild(el)
            }
        }
    })
        .catch(function (error) {
            console.log(error)
        })

}

function buildFieldErrorLabel(message) {
    let p = document.createElement("p");
    p.setAttribute("name", "error");
    p.innerHTML = message;
    p.style.color = "red";
    return p;
}