import {formatDate, getCookieValue} from "./utilitaires/utils.js";

const currentIdUser = getCookieValue("userId");
localStorage.getItem("userId");
const currentUsername = getCookieValue("username");

document.addEventListener("DOMContentLoaded", () => {
    getAllMessages();
    scrollVersLeBas();
})

//Appel tout les 3 secondes
setInterval(() => {
    getAllMessages();
    scrollVersLeBas();
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

// Récupère tous les messages de ses amis
function getAllMessages() {
    fetch('messageprivejson', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },

    })
        .then(response => response.json())  // Traitement de la réponse (JSON ici)
        .then(data => {
            console.log(data);
            sessionStorage.setItem('allMessages', JSON.stringify(data));
            let mettreAjour = true;
            // Version test
            // let mettreAjour = false;
            // if (sessionStorage.getItem('allMessages') === null) {
            //     sessionStorage.setItem('allMessages', JSON.stringify(data));
            //     mettreAjour = true;
            // } else if (sessionStorage.getItem('allMessages') !== JSON.stringify(data)) {
            //     sessionStorage.setItem('allMessages', JSON.stringify(data));
            //     mettreAjour = true;
            // } else {
            //     console.log("Les messages sont à jour");
            //     return;
            // }

            if (mettreAjour) {
                // Grouper les messages par idGroupeMessagesPrives
                const messagesByGroup = {};
                data.forEach(message => {
                    const groupId = message.idGroupeMessagesPrives;
                    if (!messagesByGroup[groupId]) {
                        messagesByGroup[groupId] = [];
                    }
                    messagesByGroup[groupId].push(message);
                });


                const listUserMessages = document.getElementById('listAllMessagesOfUser');
                listUserMessages.innerHTML = '';

                // Affichage des conversations
                Object.values(messagesByGroup).forEach(messages => {
                    messages.sort((a, b) => new Date(a.date) - new Date(b.date));

                    // Récupére le nom de son amis
                    const user = messages.find(m => m.user.id !== currentIdUser)?.user || messages[0].user;
                    const lastMessage = messages[messages.length - 1];
                    document.getElementById('idGrpMsgPrivee').value = lastMessage.idGroupeMessagesPrives;

                    const userElement = document.createElement('div');
                    userElement.className = 'user d-flex align-items-center p-2 m-1';
                    userElement.setAttribute('data-groupid', lastMessage.idGroupeMessagesPrives);
                    userElement.innerHTML = `
            <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
            <div class="m-2">
                <div class="username">${user.username}</div>
                <div class="messageUser">${lastMessage.message}</div>
            </div>`;
                    listUserMessages.appendChild(userElement);

                    // Ajouter le click pour afficher les messages de ce groupe
                    userElement.addEventListener('click', () => {
                        displayMessagesOfGroup(lastMessage.idGroupeMessagesPrives);
                    });
                });

                // Récupérer les messages de la sessionStorage
                const messages = data;
                messages.sort((a, b) => new Date(a.date) - new Date(b.date));
                const messageList = document.getElementById('listeOfMessage');
                messageList.innerHTML = '';
                // Créer un élément pour chaque message
                messages.forEach(message => {
                    let checkMessage = '';
                    const messageChecked = transformToYouTubeIframe(message.message)
                    if (messageChecked != null) {
                        checkMessage = messageChecked;
                    } else {
                        checkMessage = message.message;
                    }

                    let optionsHTML = '';
                    if (message.user.id == currentIdUser) {
                        optionsHTML += `
                            <button class="mainmenubtn boutonOptionMessage" onclick="Supprimer(${message.id})">
                                <i class="bi bi-x-lg fs-4 fw-bold"></i>
                            </button>`;
                    } else {
                        optionsHTML += `
                            <button class="mainmenubtn boutonOptionMessage" onclick="Signaler(${message.id})">
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
                            <div class="username">${message.user.username}</div>
                            <span class="ms-3 heuresMessage">${formatDate(message.date)}</span>
                        </div>
                        <div class="messageUser">${checkMessage}</div>
                    </div>
                </div>
                <div class="my-auto mx-3 rounded-circle OptionsMessage">
                    ${optionsHTML}
                </div>`;
                    messageList.appendChild(messageElement);
                });
            }

            scrollVersLeBas();
        })
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

//Affichage une liste des groupes messages privée de ses amis
function displayMessagesOfGroup(groupId) {
    const allMessages = JSON.parse(sessionStorage.getItem('allMessages') || '[]');
    const filteredMessages = allMessages.filter(msg => msg.idGroupeMessagesPrives === groupId);
    filteredMessages.sort((a, b) => new Date(a.date) - new Date(b.date));

    const messageList = document.getElementById('listeOfMessage');
    messageList.innerHTML = '';

    document.getElementById('idGrpMsgPrivee').value = lastMessage.idGroupeMessagesPrives;

    filteredMessages.forEach(message => {
        const isOwnMessage = message.user.id === currentIdUser;

        const messageElement = document.createElement('div');
        messageElement.className = 'message d-flex justify-content-between mt-2 p-2';

        messageElement.innerHTML = `
        <div class="d-flex">
            <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
            <div class="ms-3">
                <div class="d-flex flex-wrap align-items-center">
                    <div data-user-id="${message.user.id}" class="username">${message.user.username}</div>
                    <span class="ms-3 heuresMessage">${formatDate(message.date)}</span>
                </div>
                <div class="messageUser">${message.message}</div>
            </div>
        </div>
        <div class="my-auto mx-3 rounded-circle OptionsMessage">
            <button class="mainmenubtn boutonOptionMessage" onclick="Supprimer(${message.id})" href="">
                <i class="bi bi-x-lg fs-4 fw-bold"></i>
            </button>
            <button class="mainmenubtn boutonOptionMessage" onclick="Signaler(${message.id})" href="">
                <i class="bi bi-flag fs-4 fw-bold"></i>
            </button>
        </div>`;
        messageList.appendChild(messageElement);
    });

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