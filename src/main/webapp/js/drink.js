function onCreate(event) {
    event.preventDefault();
    let data = new FormData(document.getElementById('create-drink'));
    sendForm('/api/drinks', 'POST', data);
}

function onEdit(event) {
    event.preventDefault();
    let form = document.getElementById('create-drink');
    let id = form.dataset["id"];
    let data = new FormData(form);
    sendForm('/api/drinks/' + id, 'PUT', data);
}

function sendForm(url, method, data) {
    return fetch(url,
        {
            method: method,
            body: data
        })
        .then(response => {
            if (response.ok) {
                console.log(response.code);
            } else {
                //TODO: отобразить ошибки
            }
        });
}