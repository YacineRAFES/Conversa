export function createAlert(message, type){
    const alert = document.createElement("div");
    alert.className = `alert alert-${type} text-center`;
    alert.role = "alert";
    alert.innerHTML = message;
    return alert;
}