import { getCsrfToken } from "./CSRFToken.js";
import { createAlert, csrfInput } from "./constructElement.js";
import { sendFormData } from "./sendForm.js";
import { AlertMessages } from "./alertMessages.js";


const type = "home";
document.addEventListener('DOMContentLoaded', async () => {

});

// document.addEventListener("DOMContentLoaded", async function () {
//     const csrfToken = await getCsrfToken(type);
//     if (csrfToken) {
//         const form = document.getElementById("loginForm");
//         if (form) {
//             form.appendChild(csrfInput(csrfToken));
//         }
//     }
// });

// function resetCaptcha(){
//     if(window.turnstile){
//         window.turnstile.reset();
//     }
// }