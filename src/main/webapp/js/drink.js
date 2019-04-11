function onCreate(event) {
    event.preventDefault();
    let data = new FormData(document.getElementById('create-drink'));
    sendForm('/api/drinks', 'POST', data, "Новый напиток успешно создан");
}

function onEdit(event) {
    event.preventDefault();
    let form = document.getElementById('create-drink');
    let id = form.dataset["id"];
    let data = new FormData(form);
    sendForm('/api/drinks/' + id, 'PUT', data, "Изменения успешно сохранены");
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
    let categories = [];
    for (let i in object.categories) {
        categories.push(parseInt(object.categories[i]));
    }
    object.categories = categories;
    object.images = [document.getElementById("uploaded-files-input").value];
    let json = JSON.stringify(object);
    return fetch(url,
        {
            method: method,
            body: json,
            headers: {"Content-Type": "application/json"}
        })
        .then(response => {
                if (response.ok) {

                    let title = document.getElementById("modal-title");
                    title.innerHTML = "Сохранение напитка";

                    let body = document.getElementById("modal-body");
                    body.innerHTML = msg;

                    $('#overlay').modal('show');

                    setTimeout(function () {
                        $('#overlay').modal('hide');
                    }, 2000);
                    setTimeout(function () {
                        window.location = window.location.origin + "/edit/drinks";
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
                    } else if (json.errorDtos[i].field === "volume") {
                        div = document.getElementById("volume-div");
                    } else if (json.errorDtos[i].field === "description") {
                        div = document.getElementById("description-div");
                    } else if (json.errorDtos[i].field === "categories") {
                        div = document.getElementById("categories-div");
                    }
                    div.appendChild(el)
                }
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

function onUploadImage(event) {
    event.preventDefault();
    let elementById = document.getElementById("add-img")[0].files[0];
    let data = new FormData();
    data.append("image", elementById);
    fetch("/api/images",
        {
            method: "POST",
            body: data,
        })
        .then(response => {
            if (response.ok) {
                console.log(response.code);
                return response.text();
            } else {
                //TODO: отобразить ошибки
            }
        })
        .then(filePath => {
            console.log(filePath);
            let input = document.getElementById("uploaded-files-input");
            input.setAttribute("value", filePath);
            input.setAttribute("readonly", "true");
            let picDiv = document.getElementById("picture-div");
            picDiv.style.cssText = "display:none";
        })
}

function buildFieldErrorLabel(message) {
    let p = document.createElement("p");
    p.setAttribute("name", "error");
    p.innerHTML = message;
    p.style.color = "red";
    return p;
}