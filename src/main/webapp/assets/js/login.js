import {getCsrfToken} from "./CSRFToken.js";
import {csrfInput} from "./constructElement.js";
import {mdpverif} from "./MDPVerif";
import {sendFormData} from "./sendForm";

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
        const response = await sendFormData(formObject,"login");
        if (!response) {
            document.getElementById("message").innerHTML = '<div class="alert alert-danger" role="alert">Erreur lors de la réception de la réponse du serveur.</div>';
            return;
        }
        // userCreated - ESSAI: OK
        if (response.message == "userCreated") {
            window.location = "http://localhost:8090/Conversa_war/login?info=compteCree";
            // userNotCreated - ESSAI: OK
        } else if (response.message == "userAlreadyExists") {
            document.getElementById("message").innerHTML = `<div class="alert alert-danger text-center" role="alert">Le nom ou l'email existe déjà.</div>`;
            // passwordInvalid - ESSAI: OK
        } else if (response.message == "passwordInvalid") {
            document.getElementById("message").innerHTML = '<div class="alert alert-danger text-center" role="alert">Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre.</div>';
            // emailInvalid - ESSAI: OK
        } else if (response.message == "emailInvalid") {
            document.getElementById("message").innerHTML = '<div class="alert alert-danger text-center" role="alert">L\'adresse e-mail est invalide.</div>';
            // TODO: TEST  A FAIRE
            // emptyField - ESSAI: OK
        } else if (response.message == "emptyField") {
            document.getElementById("message").innerHTML = '<div class="alert alert-danger text-center" role="alert">Tous les champs doivent être remplis.</div>';
            // emailLengthInvalid - ESSAI: OK
        } else if (response.message == "lengthInvalid") {
            document.getElementById("message").innerHTML = '<div class="alert alert-danger text-center" role="alert">Le nom d\'utilisateur ou L\'adresse e-mail ne doit pas contenir plus de 50 caractères.</div>';
            // Server problem OK
        } else {
            document.getElementById("message").innerHTML = '<div class="alert alert-danger text-center" role="alert">Erreur lors de la création du compte.</div>';
        }
    });
});

document.addEventListener("DOMContentLoaded", async function () {
    const csrfToken = await getCsrfToken();
    if (csrfToken) {
        const form = document.getElementById("loginForm");
        if (form) {
            form.appendChild(csrfInput(csrfToken));
        }
    }
});