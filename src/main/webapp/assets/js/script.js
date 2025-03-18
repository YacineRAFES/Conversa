document.addEventListener('DOMContentLoaded', function() {
    const dropdownButtons = document.querySelectorAll('.mainmenubtn');

    dropdownButtons.forEach(button => {
        button.addEventListener('click', function(event) {
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
    document.addEventListener('click', function() {
        document.querySelectorAll('.dropdown-childCustom.show').forEach(menu => {
            menu.classList.remove('show');
        });

        document.querySelectorAll('.message').forEach(msg => {
            msg.classList.remove('z-indexhigh');
            msg.classList.add('z-indexlow');
        });
    });
});
