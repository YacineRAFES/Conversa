document.addEventListener("click", function (event) {
    const dropdown = document.querySelector(".dropdown-childCustom");
    const button = document.querySelector(".mainmenubtn");

    // Vérifie si le clic est sur le bouton ou dans le menu
    if (button.contains(event.target)) {
        dropdown.classList.toggle("show"); // Ouvre ou ferme le menu
    } else if (!dropdown.contains(event.target)) {
        dropdown.classList.remove("show"); // Ferme si on clique ailleurs
    }
});
