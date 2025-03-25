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

async function afficherListeUser() {
    //
    var users = "http://localhost:8080/ConversaAPI_war/api/users";
    const reponse = await fetch(users);
    const data = await reponse.json();
    console.log(data);
    let output = '';
    for (let user of data) {
        output += `
            <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h4 class="card-title">${user.name}"</h4>
                                    <h6 class="fw-bold">${user.email}</h6>
                                    <p class="card-text ">
                                        role : ${user.role}
                                        date de création : ${user.date}
                                    </p>
                                </div>
                            </a>`
    }
    document.getElementById("listUser").innerHTML = output;
}
afficherListeUser();
