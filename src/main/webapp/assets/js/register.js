import { sendFormData } from "./sendForm.js";
import { csrfInput, createAlert } from "./constructElement.js";
import { AlertMessages } from "./alertMessages.js";

const type = "user";

document.addEventListener('DOMContentLoaded', async () => {
    const form = document.getElementById("registerForm");

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
            message.appendChild(createAlert(AlertMessages.ErrorServer,"danger"));
            resetCaptcha();
            return;
        }
        if (response.message == "userCreated") {
            window.location = "./login?info=compteCree";
        } else if (response.message == "userAlreadyExists") {
            message.appendChild(createAlert(AlertMessages.UserAlreadyExists,"warning"));
            resetCaptcha();
        } else if (response.message == "passwordInvalid") {
            message.appendChild(createAlert(AlertMessages.PasswordInvalid,"warning"));
            resetCaptcha();
        } else if (response.message == "emailInvalid") {
            message.appendChild(createAlert(AlertMessages.EmailInvalid,"warning"));
            resetCaptcha();
        } else if (response.message == "emptyField") {
            message.appendChild(createAlert(AlertMessages.EmptyField,"warning"));
            resetCaptcha();
        } else if (response.message == "lengthInvalid") {
            message.appendChild(createAlert(AlertMessages.LengthInvalid,"warning"));
            resetCaptcha();
        } else {
            message.appendChild(createAlert(AlertMessages.ErrorUserCreated,"danger"));
            resetCaptcha();
        }
    });
});

// Fonction pour réinitialiser le captcha
function resetCaptcha(){
    if(window.turnstile){
        window.turnstile.reset();
    }
}