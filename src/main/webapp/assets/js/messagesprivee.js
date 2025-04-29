import { formatDate, getCookieValue } from "./utilitaires/utils.js";

const currentIdUser = getCookieValue("userId"); localStorage.getItem("userId");
const currentUsername = getCookieValue("username");

document.addEventListener("DOMContentLoaded", () => {
    getAllMessages();
})

//Appel tout les 3 secondes
setInterval(() => {
    getAllMessages();
}, 3000);

document.getElementById('sendMsg').addEventListener("click", () => sendMessage());

document.getElementById('Msg').addEventListener("keydown", function (e) {
    if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault(); // empêche la création d'une nouvelle ligne
        sendMessage();
    }
});

// Récupère tous les messages de ses amis
function getAllMessages() {
    const type = "getAllMessages"
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
                const lastMessage = messages[messages.length - 1];
                const user = lastMessage.user;
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

                // ➕ Ajouter le click pour afficher les messages de ce groupe
                userElement.addEventListener('click', () => {
                    displayMessagesOfGroup(lastMessage.idGroupeMessagesPrives);
                });
            });

            // Affichage chronologique de tous les messages
            const messageList = document.getElementById('listeOfMessage');
            messageList.innerHTML = '';

            data.sort((a, b) => new Date(a.date) - new Date(b.date));

            let lastUserId = null;
            let currentBlock = null;

            data.forEach(message => {
                const isSameUser = lastUserId === message.user.id;

                if (!isSameUser) {
                    // Créer un nouveau bloc message complet (avatar + nom + message)
                    currentBlock = document.createElement('div');
                    currentBlock.className = 'message d-flex justify-content-between mt-2 p-2';
                    currentBlock.innerHTML = `
            <div class="d-flex">
                <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
                <div class="ms-3">
                    <div class="d-flex flex-wrap align-items-center">
                        <div data-user-id="${message.user.id}" class="username">${message.user.username}</div>
                        <span class="ms-3 heuresMessage">${formatDate(message.date)}</span>
                    </div>
                    <div class="messages-group">
                        <div class="messageUser">${message.message}</div>
                    </div>
                </div>
            </div>
            <div class="dropdownCustom my-auto mx-3 rounded-circle">
                <button class="mainmenubtn boutonOptionMessage">
                    <i class="bi bi-three-dots fs-4 fw-bold"></i>
                </button>
                <ul class="dropdown-childCustom">
                    <li class="dropdown-list"><a href="">Signaler le message</a></li>
                </ul>
            </div>
        `;
                    messageList.appendChild(currentBlock);
                } else {
                    // Ajouter le message dans le bloc courant (sans avatar ni nom)
                    const group = currentBlock.querySelector('.messages-group');
                    const messageUser = document.createElement('div');
                    messageUser.className = 'messageUser';
                    messageUser.textContent = message.message;
                    group.appendChild(messageUser);
                }

                lastUserId = message.user.id;
            });
        });
}

// Envoie le message au serveur
async function sendMessage() {
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
            '&message=' + encodeURIComponent(Msg) +
            '&csrfToken=' + encodeURIComponent(csrfToken) +
            '&idGroupeMessagesPrives=' + encodeURIComponent(groupId)
    })
        .then(response => response.json())  // Traitement de la réponse (JSON ici)
        .then(data => console.log(data))

    if (Msg) {
        const lastMessageBlock = messageList.lastElementChild;
        const lastIdUser = lastMessageBlock?.querySelector(".username")?.getAttribute("data-user-id");

        // À adapter avec le vrai username côté client (tu peux le stocker dans sessionStorage si besoin)

        console.log("lastIdUser:" + lastIdUser);
        console.log("currentIdUser:" +  currentIdUser);
        console.log("currentUsername:" +  currentUsername);
        if (lastIdUser === currentIdUser) {
            // Ajout dans le dernier bloc existant
            const messageUserDiv = lastMessageBlock?.querySelector(".messageUser");
            messageUserDiv.innerHTML += `<br>${Msg}`;
        } else {
            // Sinon, création d'un nouveau bloc
            messageList.innerHTML += `<div class="message d-flex justify-content-between mt-2 p-2">
            <div class="d-flex">
                <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
                <div class="ms-3">
                    <div data-user-id="${currentIdUser}" class="username">${currentUsername}</div>
                    <div class="messageUser">${Msg}</div>
                </div>
            </div>
            <div class=" dropdownCustom my-auto mx-3 rounded-circle">
                <button class="mainmenubtn boutonOptionMessage" href="">
                    <i class="bi bi-three-dots fs-4 fw-bold"></i>
                </button>
                <ul class="dropdown-childCustom">
                    <li class="dropdown-list"><a href="">Répondre</a></li>
                    <li class="dropdown-list"><a href="">Signaler le message</a></li>
                </ul>
            </div>
        </div>`;
        }
    }


    //Réinitialiser le textarea
    document.getElementById('Msg').value = '';
}

function displayMessagesOfGroup(groupId) {
    const allMessages = JSON.parse(sessionStorage.getItem('allMessages') || '[]');
    const filteredMessages = allMessages.filter(msg => msg.idGroupeMessagesPrives === groupId);
    filteredMessages.sort((a, b) => new Date(a.date) - new Date(b.date));

    const messageList = document.getElementById('listeOfMessage');
    messageList.innerHTML = '';

    document.getElementById('idGrpMsgPrivee').value = lastMessage.idGroupeMessagesPrives;

    filteredMessages.forEach(message => {
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
        <div class="dropdownCustom my-auto mx-3 rounded-circle">
            <button class="mainmenubtn boutonOptionMessage">
                <i class="bi bi-three-dots fs-4 fw-bold"></i>
            </button>
            <ul class="dropdown-childCustom">
                <li class="dropdown-list"><a href="">Signaler le message</a></li>
            </ul>
        </div>`;
        messageList.appendChild(messageElement);
    });
}

