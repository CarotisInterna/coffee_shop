document.addEventListener("DOMContentLoaded", function () {
    fetch("/api/drinks")
        .then(function (response) {
            if (response.ok) {
                return response.json();
            } else {
                //TODO: сказать об ошибке
            }
        }).then(function (drinks) {
        //TODO : пройти по списку, создать элементы, отображающие напитки, добавить к странице

    }).catch(function (error) {
        console.log(error);
        //TODO: показать алерт с сообщением
    })
});