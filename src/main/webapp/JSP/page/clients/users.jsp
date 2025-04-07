<%--
  Created by IntelliJ IDEA.
  User: USER-10
  Date: 25/03/2025
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/assets/css/bootstrap.css" var="bootstrap"/>
<c:url value="/assets/css/style.css" var="style"/>
<c:url value="/assets/css/bootstrapicons.css" var="bootstrapicons"/>
<c:url value="/assets/js/script.js" var="script"/>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Connexion - Conversa</title>
    <link rel="stylesheet" href="${bootstrap}">
    <link rel="stylesheet" href="${style}">
    <link rel="stylesheet" href="${bootstrapicons}">
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
                        <a class="nav-link btn-sidebar" href="home.jsp">
                            <i class="bi bi-house fs-2" aria-label="Accueil"></i>
                        </a>
                    </li>
                    <!-- BOUTON DE MESSAGES PRIVEES -->
                    <li class="nav-item mb-1">
                        <a class="nav-link btn-sidebar" href="clients/messagesPrivate.jsp">
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
                        <!-- LISTE DES USERS -->
                        <div id="listUser" class="d-flex d-inline-flex pt-3 flex-wrap overflow-y-scroll overflow-y-hidden" style="scrollbar-width: none;  height: calc(80vh - 1rem);">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- LIEN DE SCRIPT -->
<script src="../../assets/js/bootstrap.js"></script>
<script src="${script}"></script>
</body>

</html>