<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="/WEB-INF/JSP/header.jsp" %>
<main>
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
                <div class="message disparition">
                    <c:if test="${param.info == 'compteCree'}">
                        <div class="alert alert-success text-center mb-3" role="alert">Votre compte a été crée. Veuillez de vous connecter.</div>
                    </c:if>
                </div>
                <!-- FORMULAIRE DE CONNEXION -->
                <form id="loginForm" action="${pageContext.request.contextPath}/login" class="form-control rounded-0 p-5 mx-auto my-auto" method="post">
                    <!-- CSRF TOKEN -->
                    <input type="hidden" name="csrfToken" value="<c:out value='${requestScope.csrfToken}'/>"/>
                    <!-- TITRE DU FORMULAIRE -->
                    <div class="mb-3">
                        <h5 class="text-center">Connexion</h5>
                    </div>
                    <!-- CHAMPS D'EMAIL -->
                    <div class="mb-3">
                        <label for="userEmail" class="form-label">
                            Adresse email <span aria-hidden="true">*</span>
                        </label>
                        <input type="email" name="email" class="form-control rounded-0" id="userEmail" aria-label="Adresse email" aria-required="true" required>
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                    </div>
                    <!-- CHAMPS DE MOT DE PASSE -->
                    <div class="mb-3">
                        <label for="password" class="form-label">
                            Mot de passe <span aria-hidden="true">*</span>
                        </label>
                        <input type="password"
                               name="password"
                               class="form-control rounded-0 text-decoration-none"
                               id="password"
                               aria-label="Mot de passe"
                               aria-required="true"
                               required>
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                        <!-- LIEN HYPERTEXTE POUR OUVRIR LE MODAL -->
                        <a data-bs-toggle="modal" data-bs-target="#motdepasseoublie" class="">
                            Mot de passe oublié
                        </a>
                    </div>
                    <!-- CAPTCHA -->
                    <div class="cf-turnstile" data-sitekey="0x4AAAAAABCkJ2clzFt4U0Yt"></div>
                    <!-- BOUTON DE CONNEXION -->
                    <div class="text-center mt-4">
                        <button type="submit" value="Submit" class="btn btn-outline-success rounded-0 px-3">Se connecter</button>
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
</main>
<%@include file="/WEB-INF/JSP/footer.jsp" %>