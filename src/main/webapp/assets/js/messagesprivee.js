document.addEventListener("DOMContentLoaded", () => {
    getAllMessages();
})

document.getElementById('sendMsg').addEventListener("click", () => sendMessage());

document.getElementById('Msg').addEventListener("keydown", function (e) {
    if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault(); // empêche la création d'une nouvelle ligne
        sendMessage();
    }
});

// Récupère tous les messages de ses amis
function getAllMessages(){
    const type = "getAllMessages"
    fetch('messageprive', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'type=' + encodeURIComponent(type)

    })
        .then(response => response.json())  // Traitement de la réponse (JSON ici)
        .then(data => console.log(data))
}

async function sendMessage() {
    const type = "sendMessages"
    //Récuperer le message dans le textarea
    const Msg = document.getElementById('Msg').value;
    const csrfToken = document.getElementById('csrfToken').value;

    //Ajoute le message à la liste des messages
    const messageList = document.getElementById('listeOfMessage');

    //Envoyer le message au serveur
    fetch('messageprive', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'type=' + type + '&message=' + encodeURIComponent(Msg) + '&csrfToken=' + encodeURIComponent(csrfToken)

    })
        .then(response => response.json())  // Traitement de la réponse (JSON ici)
        .then(data => console.log(data))

    if(Msg){
        messageList.innerHTML += `<div class="message d-flex justify-content-between mt-2 p-2">
                                    <div class="d-flex">
                                        <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
                                        <div class="ms-3">
                                            <div class="username">Username</div>
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
                                </div>`
    }

    //Réinitialiser le textarea
    document.getElementById('Msg').value = '';


}
