import { createAlert, csrfInput } from "./constructElement.js";
import { AlertMessages } from "./alertMessages.js";
import {loginUser} from "./loginUser.js";


const type = "login";
document.addEventListener('DOMContentLoaded', async () => {
    const form = document.getElementById("loginForm");
    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        const formData = new FormData(this); // Création du FormData avec l'objet formulaire

        // Création d'un objet pour stocker les données sous forme de JSON
        const formObject = {};
        formData.forEach((value, key) => {
            formObject[key] = value;
        });

        // Envoi du formulaire avec le token
        const response = await loginUser(formObject);

        const message = document.getElementById("message");
        if (!response) {
            message.appendChild(createAlert(AlertMessages.ErrorServer, "danger"));
        }
        if (response.message === "loginSuccess") {
            window.location.href = "./home";
        } else if (response.message === "InvalidCredentials") {
            message.appendChild(createAlert(AlertMessages.InvalidCredentials,"warning"));
            resetCaptcha();
        } else {
            message.appendChild(createAlert(AlertMessages.ErrorCredentials,"danger"));
            resetCaptcha();
        }
    });
});

function resetCaptcha(){
    if(window.turnstile){
        window.turnstile.reset();
    }
}