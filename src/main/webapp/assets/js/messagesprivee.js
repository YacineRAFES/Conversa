import {formatDate, getCookieValue} from "./utilitaires/utils.js";
let currentGroupId= null;
currentGroupId = localStorage.getItem('groupId');

localStorage.getItem("userId");
const currentIdUser = getCookieValue("userId")
console.log(currentIdUser);
document.addEventListener("DOMContentLoaded", () => {
    // getAllMessages();
    // scrollVersLeBas();
    getAllAmis();
})

//Appel tout les 3 secondes
setInterval(() => {
    if (currentGroupId !== null) {
        getAllMessagesByIdGrpMsg(currentGroupId);
    }
}, 3000);

document.getElementById('sendMsg').addEventListener("click", () => Message("sendMessages"));

document.getElementById('Msg').addEventListener("keydown", function (e) {
    if (e.key === "Enter" && !e.shiftKey) {
        const type = "sendMessages";
        e.preventDefault(); // empêche la création d'une nouvelle ligne
        Message(type);
    }
});

function scrollVersLeBas() {
    const messageList = document.getElementById('listeOfMessage');
    messageList.scrollTop = messageList.scrollHeight;
}

function getAllAmis() {
    fetch('amisjson', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    })
        .then(response => response.json())
        .then(data => {
            console.log("Amis :", data);
            const listAllMessagesOfUser = document.getElementById('listAllMessagesOfUser');
            listAllMessagesOfUser.innerHTML = '';

            data.forEach((amis) => {
                const userElement = document.createElement('div');
                userElement.className = 'user d-flex align-items-center p-2 m-1';
                userElement.setAttribute('data-groupid', amis.idGroupeMessagesPrives);
                userElement.innerHTML = `
                <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
                <div class="m-2">
                    <div class="username">${amis.username}</div>
                </div>`;

                listAllMessagesOfUser.appendChild(userElement);

                // Ajouter l'action de clic
                userElement.addEventListener('click', () => {
                    localStorage.setItem('groupId', amis.idGroupeMessagesPrives);
                    document.getElementById('idGrpMsgPrivee').value = amis.idGroupeMessagesPrives;
                    getAllMessagesByIdGrpMsg(amis.idGroupeMessagesPrives);
                });
            });
        });
}

function getAllMessagesByIdGrpMsg(idgroupemp){
    fetch('messageprivejson', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    })
        .then(response => response.json())  // Traitement de la réponse (JSON ici)
        .then(data => {
            const messageList = document.getElementById('listeOfMessage');
            messageList.innerHTML = '';
            data.forEach(item => {
                const groupe = item.groupe_messages_prives;
                if(groupe.id_groupe_messages_prives == idgroupemp) {

                    const msg = groupe.message_prive;

                    //Verifie si le message est un lien de youtube
                    let checkMessage = '';
                    const messageChecked = transformToYouTubeIframe(msg.message)
                    if (messageChecked != null) {
                        checkMessage = messageChecked;
                    } else {
                        checkMessage = msg.message;
                    }

                    //Mise en place les boutons pour signaler et supprimer le message
                    let optionsHTML = '';
                    if (msg.user.id_user == currentIdUser) {
                        optionsHTML += `
                            <button class="mainmenubtn boutonOptionMessage" onclick="Supprimer(${msg.id_message_prive})">
                                <i class="bi bi-x-lg fs-4 fw-bold"></i>
                            </button>`;
                    } else {
                        optionsHTML += `
                            <button class="mainmenubtn boutonOptionMessage" onclick="Signaler(${msg.id_message_prive})">
                                <i class="bi bi-flag fs-4 fw-bold"></i>
                            </button>`;
                    }
                    const messageElement = document.createElement('div');
                    messageElement.className = 'message d-flex justify-content-between mt-2 p-2';
                    messageElement.innerHTML = `
                            <div class="d-flex">
                                <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
                                <div class="ms-3">
                                    <div class="d-flex flex-wrap align-items-center">
                                        <div class="username">${msg.user.username}</div>
                                        <span class="ms-3 heuresMessage">${formatDate(msg.date)}</span>
                                    </div>
                                    <div class="messageUser">${checkMessage}</div>
                                </div>
                            </div>
                            <div class="my-auto mx-3 rounded-circle OptionsMessage">
                                ${optionsHTML}
                            </div>`;
                    messageList.appendChild(messageElement);
                    scrollVersLeBas();
                }
            });
        })
        .catch(error => {
            console.error('Erreur lors de la récupération des messages :', error);
        });
}

// Envoie le message au serveur
async function Message(type) {
    //Récuperer le message dans le textarea
    const Msg = document.getElementById('Msg').value;
    const groupId = document.getElementById('idGrpMsgPrivee').value;
    const csrfToken = document.getElementById('csrfToken').value;

    //Ajoute le message à la liste des messages
    const messageList = document.getElementById('listeOfMessage');

    //Envoyer le message au serveur
    fetch('messageprive', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body:
            'type=' + encodeURIComponent(type) +
            '&message=' + encodeURIComponent(Msg) +
            '&csrfToken=' + encodeURIComponent(csrfToken) +
            '&idGroupeMessagesPrives=' + encodeURIComponent(groupId)
    })
        .then(response => response.json())  // Traitement de la réponse (JSON ici)
        .then(data => console.log(data))

    document.getElementById('Msg').value = '';
    scrollVersLeBas();
}

//Signaler un message
function Signaler(idMessage) {
    const csrfToken = document.getElementById('csrfToken').value;
    fetch('messageprive', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body:
            'type=' + encodeURIComponent('signaler') +
            '&idMessage=' + encodeURIComponent(idMessage) +
            '&csrfToken=' + encodeURIComponent(csrfToken) +
            '&idGroupeMessagesPrives=' + encodeURIComponent(document.getElementById('idGrpMsgPrivee').value)
    })
        .then(response => response.json())
        .then(data => console.log(data))
}

//Supprimer un message
function Supprimer(idMessage) {
    const csrfToken = document.getElementById('csrfToken').value;
    fetch('messageprive', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body:
            'type=' + encodeURIComponent('supprimer') +
            '&idMessage=' + encodeURIComponent(idMessage) +
            '&csrfToken=' + encodeURIComponent(csrfToken) +
            '&idGroupeMessagesPrives=' + encodeURIComponent(document.getElementById('idGrpMsgPrivee').value)
    })
        .then(response => response.json())
        .then(data => console.log(data))
}

window.Supprimer = Supprimer;
window.Signaler = Signaler;

// Fonction pour transformer une URL YouTube en iframe
function transformToYouTubeIframe(url) {
    const regex = /^(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/(?:watch\?v=|embed\/)|youtu\.be\/)([a-zA-Z0-9_-]{11})/;
    const match = url.match(regex);

    if (match && match[1]) {
        const videoId = match[1];
        return `<iframe width="560" height="315" src="https://www.youtube.com/embed/${videoId}"
            title="YouTube video player" frameBorder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
            referrerPolicy="strict-origin-when-cross-origin"
            allowFullScreen></iframe>`;
    }
    return null; // ce n'est pas une vidéo YouTube
}
