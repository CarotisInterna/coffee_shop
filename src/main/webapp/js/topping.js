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
    let object = {};
    data.forEach((value, key) => {object[key] = value});
    let json = JSON.stringify(object);
    return fetch(url,
        {
            method: method,
            headers: {"Content-Type": "application/json"},
            body: json
        })
        .then(response => {
        if (response.ok) {
        console.log(response.code);
    } else {
        //TODO: отобразить ошибки
    }
});
}