export function csrfInput(token){
    const csrfInput = document.createElement("input");
    csrfInput.type = "hidden";
    csrfInput.name = "csrf";
    csrfInput.id = "csrfToken";
    csrfInput.value = token;
    return csrfInput;
}