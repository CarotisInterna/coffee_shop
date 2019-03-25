function onCreate(form) {
    let data = new FormData(document.getElementById('create-drink'));
    for (var [key, value] of data.entries()) {
        console.log(key, value);
    }
    return false;
}