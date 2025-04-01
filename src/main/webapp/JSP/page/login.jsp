<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="/WEB-INF/JSP/header.jsp" %>

<body class="d-flex flex-column min-vh-100 bg-img">
    <!-- HEADER -->
    <header>
        <nav class="navbar navbar-expand-lg bg-info">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Conversa</a>
            </div>
        </nav>
    </header>
    <!-- PARTIE DE CORPS DU SITE -->
    <div class="container">
        <div id="message"></div>
        <div class="row d-flex">
            <div class="col-4 mx-auto mt-5">

                <c:if test="${param.info == 'compteCree'}">
                    <div class="alert alert-success text-center mb-3" role="alert">Votre compte a été crée. Veuillez de vous connecter.</div>
                </c:if>
                <!-- FORMULAIRE DE CONNEXION -->
                <form id="loginForm" class="form-control rounded-0 p-5" action="" method="post">
                    <!-- TITRE DU FORMULAIRE -->
                    <div class="mb-3">
                        <h5 class="text-center">Connexion</h5>
                    </div>
                    <!-- CHAMPS D'EMAIL -->
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control rounded-0" id="email">
                    </div>
                    <!-- CHAMPS DE MOT DE PASSE -->
                    <div class="mb-3">
                        <label for="password" class="form-label">Mot de passe</label>
                        <input type="password" class="form-control rounded-0 text-decoration-none" id="password">
                        <!-- LIEN HYPERTEXTE POUR OUVRIR LE MODAL -->
                        <a href="" data-bs-toggle="modal" data-bs-target="#motdepasseoublie" class="">
                            Mot de passe oublié
                        </a>
                    </div>
                    <!-- CAPTCHA -->
                    <div class="cf-turnstile" data-sitekey="0x4AAAAAABCkJ2clzFt4U0Yt"></div>
                    <!-- BOUTON DE CONNEXION -->
                    <div class="text-center mt-4">
                        <input type="submit" class="btn btn-outline-success rounded-0" value="Se connecter">
                    </div>
                    <!-- PARTIE POUR REDIRIGER DANS UNE PAGE POUR CREER UN COMPTE -->
                    <div class="text-center mt-1">
                        <a href="${pageContext.request.contextPath}/register">
                            Je n'ai pas de compte
                        </a>
                    </div>
                </form>
                <!-- MODAL POUR REINITIALISER LE MOT DE PASSE -->
                <div class="modal fade" id="motdepasseoublie" tabindex="-1" aria-labelledby="ModalReset" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <!-- FORMULAIRE DE REINITIALISER LE MOT DE PASSE -->
                            <form id="resetForm" action="" class="form-control border-0 p-0" method="post">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="ModalReset">Réinitialisation de mot de passe</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <input type="text" name="AskEmailForReset" class="form-control"
                                        id="AskEmailForReset" placeholder="Votre email">
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-outline-info rounded-0">Valider</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <!-- LIEN DE SCRIPT -->
    <script src="../../assets/js/bootstrap.js"></script>
</body>

</html>