function onCreate(event) {
    event.preventDefault();
    let data = new FormData(document.getElementById('create-topping'));
    sendForm('/api/toppings', 'POST', data, "Новый топпинг успешно создан");
}

function onEdit(event) {
    event.preventDefault();
    let form = document.getElementById('create-topping');
    let id = form.dataset["id"];
    let data = new FormData(form);
    sendForm('/api/toppings/' + id, 'PUT', data, "Изменения успешно сохранены");
}

function sendForm(url, method, data, msg) {

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

            let title = document.getElementById("modal-title");
            title.innerHTML = "Сохранение топпинга";

            let body = document.getElementById("modal-body");
            body.innerHTML = msg;

            $('#overlay').modal('show');

            setTimeout(function() {
                $('#overlay').modal('hide');
            }, 2000);
            setTimeout(function () {
                window.location = window.location.origin + "/edit/toppings";
            }, 2000);
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

function changeLocation() {
    window.location = window.location.origin + "/edit/toppings";
}