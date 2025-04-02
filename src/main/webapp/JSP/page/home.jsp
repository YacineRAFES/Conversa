<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Connexion - Conversa</title>
    <link rel="stylesheet" href="../../assets/css/bootstrap.css">
    <link rel="stylesheet" href="../../assets/css/style.css">
    <link rel="stylesheet" href="../../assets/css/bootstrapicons.css">
</head>

<body class="min-vh-100 bg-gradient-custom">
    <!-- PARTIE DE CORPS DU SITE -->
    <div class="container-fluid">
        <div class="row" style="height: 100vh; ">
            <!-- SIDEBAR MENU DE L'APPLICATION -->
            <div class="p-auto d-flex flex-column my-2" style="width: auto;">
                <ul class="nav flex-column flex-grow-1 justify-content-between mx-auto">
                    <div>
                        <!-- BOUTON DE DECONNEXION -->
                        <li class="nav-item mb-1">
                            <a class="nav-link btn-sidebar-for-power btn-sidebar-power click-to-red" href="#">
                                <i class="bi bi-power fs-2" aria-label="Déconnexion"></i>
                            </a>
                        </li>
                        <!-- BOUTON DE ACCUEIL -->
                        <li class="nav-item mb-1">
                            <a class="nav-link btn-sidebar" href="home.html">
                                <i class="bi bi-house fs-2" aria-label="Accueil"></i>
                            </a>
                        </li>
                        <!-- BOUTON DE MESSAGES PRIVEES -->
                        <li class="nav-item mb-1">
                            <a class="nav-link btn-sidebar" href="messagesPrivate.html">
                                <i class="bi bi-chat-right fs-2" aria-label="Messages privées"></i>
                            </a>
                        </li>
                        <!-- BOUTON DE CONTACTS -->
                        <li class="nav-item mb-1">
                            <a class="nav-link btn-sidebar" href="#">
                                <i class="bi bi-person-lines-fill fs-2" aria-label="Contacts"></i>
                            </a>
                        </li>
                        <!-- BOUTON DE GROUPES -->
                        <li class="nav-item mb-1">
                            <a class="nav-link btn-sidebar" href="#">
                                <i class="bi bi-grid-fill fs-2" aria-label="Groupes"></i>
                            </a>
                        </li>
                    </div>
                    <li class="nav-item">
                        <!-- TODO Logo à changer pour super admin
                    <a class="nav-link btn-sidebar text-decoration-none text-white" href="#">
                        <i class="bi bi-shield-shaded align-self-end"></i></i>
                    </a> -->
                    <!-- BOUTON DE PARAMETRES -->
                        <a class="nav-link btn-sidebar" href="#">
                            <i class="bi bi-gear fs-2" aria-label="Paramètres"></i>
                            
                        </a>
                    </li>
                </ul>

            </div>
            <!-- COLONNE PRINCIPAL -->
            <div class="col p-0 bloc-principal me-2">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col mt-3">
                            <!-- FORMULAIRE DE RECHERCHE D'UNE COMMUNAUTE -->
                            <form action="" method="post">
                                <div class="mb-3 d-flex justify-content-center">
                                    <input type="text" class="placeholderCustom form-control rounded-3" id="SearchGroup"
                                        style="width: 20%;" placeholder="Rechercher une communauté...">
                                </div>
                            </form>
                            <!-- TITRE DE BIENVENUE -->
                            <div class="text-center fw-bold fs-4 text-white">
                                Bienvenue sur l'application Conversa
                            </div>
                            <!-- LISTE DES CATEGORIES -->
                            <div class="d-flex justify-content-center mt-3">
                                <ul class="nav nav-pills">
                                    <li class="nav-item">
                                        <a class="nav-link fw-bold text-white" href="#">Tout</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white" href="#">Divertissement</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white" href="#">Manga</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white" href="#">Séries</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white" href="#">Films</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white" href="#">Jeux-vidéos</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white" href="#">Technologie</a>
                                    </li>
                                </ul>
                            </div>
                            <!-- LISTE DES COMMUNAUTES -->
                            <div class="d-flex d-inline-flex pt-3 flex-wrap overflow-y-scroll overflow-y-hidden" style="scrollbar-width: none;  height: calc(80vh - 1rem);">
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem; max-height: 21rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="bannière de la communauté">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>
                                <a class="m-1 card text-decoration-none groupEffet align-self-stretch" href="#" style="width: 18rem;">
                                    <img src="../../assets/images/nightcity.jpg" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h4 class="card-title">Communauté</h4>
                                        <h6 class="fw-bold">Catégorie</h6>
                                        <p class="card-text ">
                                            Some quick example text to build on the card title and make
                                            up the bulk of the card's content.
                                        </p>
                                    </div>
                                </a>

                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>


    <!-- LIEN DE SCRIPT -->
    <script src="../../assets/js/bootstrap.js"></script>
    <script>

        async function afficherFilms() {
            var users = "http://localhost:8080/ConversaAPI_war/api/users";
            const reponse = await fetch(users);
            console.log(users);
        }
        afficherFilms();
    </script>
</body>

</html>