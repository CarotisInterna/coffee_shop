function login() {

    var errorMsgs = document.getElementsByName("error");

    if (errorMsgs !== null && errorMsgs.length !== 0) {
        for (let i = errorMsgs.length - 1; i >= 0; i--) {
            errorMsgs[i].remove();
        }
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
        let el;
        if (json !== null) {
            if (typeof json.errorDtos == 'undefined') {
                el = buildFieldErrorLabel(json.message);
            } else {
                el = buildFieldErrorLabel(json.errorDtos[0].message);
            }
            let div;
            if (json.field === "username" || (typeof json.errorDtos != 'undefined' && json.errorDtos[0].field == "username")) {
                div = document.getElementById("username-div");
            } else if (json.field === "password" || (typeof json.errorDtos != 'undefined' && json.errorDtos[0].field == "password")) {
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
    p.style.color = "red";
    return p;
}