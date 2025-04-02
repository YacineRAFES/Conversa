export function csrfInput(token){
    const csrfInput = document.createElement("input");
    csrfInput.type = "hidden";
    csrfInput.name = "csrf";
    csrfInput.id = "csrfToken";
    csrfInput.value = token;
    return csrfInput;
}

export function createAlert(message, type){
    const alert = document.createElement("div");
    alert.className = `alert alert-${type} text-center`;
    alert.role = "alert";
    alert.innerHTML = message;
    return alert;
}