function checkInputValidity(e) {
    let inputs = document.querySelectorAll("input:not([type=checkbox])");
    for (const input of inputs) {
        if (!input.checkValidity())
            input.classList.add("is-invalid")
        else
            input.classList.add("is-valid")
    }
    console.log(e)
}