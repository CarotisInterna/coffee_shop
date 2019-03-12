function login() {

    var errorMsgs = document.getElementsByName("error");

    if (errorMsgs !== null && errorMsgs.length !== 0) {
        errorMsgs.forEach(el => el.remove());
    }

    let username = document.getElementsByName("f")[0].elements["username"].value;
    let password = document.getElementsByName("f")[0].elements["password"].value;

    let appUserLoginDto = {
        "id": null,
        "username": username,
        "password": password
    };


    fetch('/api/login', {
        method: "POST",
        body: JSON.stringify(appUserLoginDto),
        headers: {"Content-Type": "application/json"}
    }).then(function (response) {
        if (response.ok) {
            window.location = window.location.origin + "/drinks";
        } else if (response.status === 404 || response.status === 400) {
            return response.json();
        } else if (response.status === 500) {
            alert("Ошибка сервера, повторите запрос позже")
        }
    }).then(function (json) {
        if (json !== null) {
            let el = buildFieldErrorLabel(json.message);
            let div;
            if (json.field === "username") {
                div = document.getElementById("username-div");
            } else if (json.field === "password") {
                div = document.getElementById("password-div");
            }
            div.appendChild(el)
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
    return p;
}