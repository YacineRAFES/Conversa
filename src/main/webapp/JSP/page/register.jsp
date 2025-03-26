<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="/WEB-INF/JSP/header.jsp" %>
<body class="d-flex flex-column min-vh-100 bg-img">
    <!-- HEADER -->
    <header style="z-index: 2;">
        <nav class="navbar navbar-expand-lg bg-info">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Conversa</a>
            </div>
        </nav>
    </header>
    <!-- PARTIE DE CORPS DU SITE -->
    <div class="container" style="z-index: 2;">
        <div class="row d-flex">
            <div class="col-4 mx-auto mt-5">
                <form class="form-control rounded-0 p-5" action="" method="post" id="registerForm">
                    <!-- TITRE DU FORMULAIRE -->
                    <div class="mb-3">
                        <h5 class="text-center">Créer un compte</h5>
                    </div>
                    <!-- CHAMPS DE NOM D'UTILISATEUR -->
                    <div class="mb-3">
                        <label for="userName" class="form-label">
                            Nom d'utilisateur <span aria-hidden="true">*</span>
                        </label>
                        <input type="text" class="form-control rounded-0" id="userName" aria-label="Nom d'utilisateur" required aria-required="true">
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                    </div>
                    <!-- CHAMPS DE ADRESSE D'EMAIL -->
                    <div class="mb-3">
                        <label for="userEmail" class="form-label">
                            Adresse email <span aria-hidden="true">*</span>
                        </label>
                        <input type="email" class="form-control rounded-0" id="userEmail" aria-label="Adresse email" required aria-required="true">
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                    </div>
                    <!-- CHAMPS DE MOT DE PASSE -->
                    <div class="mb-3">
                        <label for="password1" class="form-label">
                            Mot de passe <span aria-hidden="true">*</span>
                        </label>
                        <input type="password" class="form-control rounded-0" onkeyup="mdpverif()" id="password1" aria-label="Mot de passe" required aria-required="true">
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                    </div>
                    <!-- CHAMPS DE CONFIRMATION DE MOT DE PASSE -->
                    <div class="mb-3">
                        <label for="password2" class="form-label">
                            Confirmation mot de passe <span aria-hidden="true">*</span>
                        </label>
                        <input type="password" class="form-control rounded-0" onkeyup="mdpverif()" id="password2" aria-label="Confirmation de mot de passe" required aria-required="true">
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                    </div>
                    <div class="text-center">
                        <div id="mdpverif"></div>
                    </div>
                    <!-- TODO: CAPTCHA -->
                    <div>CAPTCHA A METTRE</div>
                    <!-- BOUTON DE SUBMIT DU FORMULAIRE -->
                    <div class="text-center mt-4" id="boutonSiLeMDPestValide">

                    </div>
                    <!-- LIEN HYPERTEXTE REDIRECTION VERS LA PAGE DE CONNEXION -->
                    <div class="text-center mt-1">
                        <a href="login.html">J'ai déjà un compte</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- LIEN DE SCRIPT -->
    <script src="../../assets/js/bootstrap.js"></script>
    <%@include file="/WEB-INF/JSP/footer.jsp" %>

