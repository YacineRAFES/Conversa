import { getCsrfToken } from "./CSRFToken.js";
import { createAlert, csrfInput } from "./constructElement.js";
import { sendFormData } from "./sendForm.js";
import { AlertMessages } from "./alertMessages.js";


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
        const response = await sendFormData(formObject, type);
        const message = document.getElementById("message");
        if (!response) {
            document.getElementById("message").innerHTML = '<div class="alert alert-danger" role="alert">Erreur lors de la réception de la réponse du serveur.</div>';
            return;
        }
        // sessionStorage = response.get
        // userCreated - ESSAI: OK
        // if (response.message == "loginSuccess") {
        //     window.location.href = "./home";
        //     resetCaptcha();
        // } else if (response.message == "InvalidCredentials") {
        //     message.appendChild(createAlert(AlertMessages.InvalidCredentials,"warning"));
        //     resetCaptcha();
        // } else {
        //     message.appendChild(createAlert(AlertMessages.ErrorCredentials,"danger"));
        // }
    });
});

document.addEventListener("DOMContentLoaded", async function () {
    const csrfToken = await getCsrfToken(type);
    if (csrfToken) {
        const form = document.getElementById("loginForm");
        if (form) {
            form.appendChild(csrfInput(csrfToken));
        }
    }
});

function resetCaptcha(){
    if(window.turnstile){
        window.turnstile.reset();
    }
}