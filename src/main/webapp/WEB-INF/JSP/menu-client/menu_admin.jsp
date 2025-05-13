<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- SIDEBAR MENU DE L'APPLICATION -->
<div class="p-auto d-flex flex-column my-2" style="width: auto;">
    <ul class="nav flex-column flex-grow-1 justify-content-between mx-auto">
        <div>
            <!-- BOUTON DE DECONNEXION -->
            <li class="nav-item mb-1">
                <a class="nav-link btn-sidebar-for-power btn-sidebar-power click-to-red"
                href="${pageContext.request.contextPath}/deconnexion"
                title="Déconnexion">
                    <i class="bi bi-power fs-2" aria-label="Déconnexion"></i>
                </a>
            </li>

            <!-- BOUTON D'ACCUEIL -->
            <li class="nav-item mb-1">
                <a class="nav-link btn-sidebar"
                href="${pageContext.request.contextPath}/home"
                title="Accueil">
                    <i class="bi bi-house fs-2" aria-label="Accueil"></i>
                </a>
            </li>

            <!-- BOUTON DE MESSAGES PRIVÉS -->
            <li class="nav-item mb-1">
                <a class="nav-link btn-sidebar"
                href="${pageContext.request.contextPath}/messageprive"
                title="Messages privées">
                    <i class="bi bi-chat-right fs-2" aria-label="Messages privées"></i>
                </a>
            </li>

            <!-- BOUTON DE CONTACTS -->
            <li class="nav-item mb-1">
                <a class="nav-link btn-sidebar"
                href="${pageContext.request.contextPath}/amis"
                title="Amis">
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
        <div>

            <!-- BOUTON GESTION DES COMPTES -->
            <li class="nav-item mb-1">
                <a class="nav-link btn-sidebar"
                href="${pageContext.request.contextPath}/accmanagement"
                title="Gestion des comptes">
                    <i class="bi bi-person-vcard-fill fs-2" aria-label="accmanagement"></i>
                </a>
            </li>

            <!-- BOUTON ADMIN -->
            <li class="nav-item mb-1">
                <a class="nav-link btn-sidebar"
                href="${pageContext.request.contextPath}/admin"
                title="Gestion des signalements">
                    <i class="bi bi-shield-shaded fs-2" aria-label="Admin"></i>
                </a>
            </li>

            <!-- BOUTON PARAMÈTRES -->
            <li class="nav-item">
                <a class="nav-link btn-sidebar" href="#">
                    <i class="bi bi-gear fs-2" aria-label="Paramètres"></i>
                </a>
            </li>
        </div>

    </ul>
</div>
