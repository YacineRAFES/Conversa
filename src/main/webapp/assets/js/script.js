import { sendFormData } from "./sendRegisterForm.js"
import { getCsrfToken } from "./CSRFToken.js";

document.addEventListener('DOMContentLoaded', function () {
    const dropdownButtons = document.querySelectorAll('.mainmenubtn');

    dropdownButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.stopPropagation();
            const dropdownMenu = this.nextElementSibling;
            dropdownMenu.classList.toggle('show');

            // Fermer les autres menus déroulants
            document.querySelectorAll('ul.show').forEach(menu => {
                if (menu !== dropdownMenu) {
                    menu.classList.remove('show');
                }
            });

            // Gestion du z-index pour la priorité des messages
            const messageElement = this.closest('.message');

            // Remet tous les messages en z-index bas
            document.querySelectorAll('.message').forEach(msg => {
                msg.classList.remove('z-indexhigh');
                msg.classList.add('z-indexlow');
            });

            // Applique le z-index élevé au message cliqué
            messageElement.classList.remove('z-indexlow');
            messageElement.classList.add('z-indexhigh');
        });
    });

    // Fermer les menus et réinitialiser le z-index lorsqu'on clique en dehors
    document.addEventListener('click', function () {
        document.querySelectorAll('.dropdown-childCustom.show').forEach(menu => {
            menu.classList.remove('show');
        });

        document.querySelectorAll('.message').forEach(msg => {
            msg.classList.remove('z-indexhigh');
            msg.classList.add('z-indexlow');
        });
    });
});

document.querySelectorAll("textarea").forEach(function (textarea) {
    textarea.style.height = textarea.scrollHeight + "px";

    textarea.style.overflowY = "hidden";

    textarea.addEventListener("input", function () {
        this.style.height = "auto";
        this.style.height = this.scrollHeight + "px";
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const textarea = document.getElementById("autoResize");
    const listOfMessage = document.querySelector(".listOfMessage");
    const insertMessage = document.querySelector(".insertMessage");
    
    function adjustLayout() {
        textarea.style.height = "auto";
        textarea.style.height = textarea.scrollHeight + "px";

        // Ajuste la hauteur de la liste des messages
        const newHeight = `calc(100vh - ${insertMessage.offsetHeight + 20}px)`;
        listOfMessage.style.height = newHeight;
    }

    textarea.addEventListener("input", adjustLayout);
    
    // Initialiser la mise en page
    adjustLayout();
});



document.addEventListener('DOMContentLoaded', async () => {
    const form = document.getElementById("registerForm");

    form.addEventListener("submit", function (event) {
        event.preventDefault();


        const formData = new FormData(this); // Création du FormData avec l'objet formulaire

        // Création d'un objet pour stocker les données sous forme de JSON
        const formObject = {};
        formData.forEach((value, key) => {
            formObject[key] = value;
        });

        // Envoi du formulaire avec le token
        sendFormData(formObject);
    });
});

document.addEventListener("DOMContentLoaded", async function () {
    const csrfToken = await getCsrfToken();
    if (csrfToken) {
        const csrfInput = document.createElement("input");
        csrfInput.type = "hidden";
        csrfInput.name = "csrf";
        csrfInput.id = "csrfToken";
        csrfInput.value = csrfToken;

        const form = document.getElementById("registerForm");
        if (form) {
            form.appendChild(csrfInput);
        }
    }
});