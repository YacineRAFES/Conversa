<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/JSP/header.jsp" %>
<main>
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
        <div id="message">
            <c:if test="${param.info == 'erreurCaptcha'}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>
        </div>
        <div class="row d-flex">
            <div class="col-4 mx-auto mt-5">
                <form class="form-control rounded-0 p-5" action="${pageContext.request.contextPath}/register" method="post" id="registerForm">
                    <!-- CSRF TOKEN -->
                    <input type="hidden" name="csrftoken" value="${csrfToken}"/>
                    <!-- TITRE DU FORMULAIRE -->
                    <div class="mb-3">
                        <h5 class="text-center">Créer un compte</h5>
                    </div>
                    <!-- CHAMPS DE NOM D'UTILISATEUR -->
                    <div class="mb-3">
                        <label for="userName" class="form-label">
                            Nom d'utilisateur <span aria-hidden="true">*</span>
                        </label>
                        <input type="text" name="user" class="form-control rounded-0" id="userName" aria-label="Nom d'utilisateur" aria-required="true" required>
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                    </div>
                    <!-- CHAMPS DE ADRESSE D'EMAIL -->
                    <div class="mb-3">
                        <label for="userEmail" class="form-label">
                            Adresse email <span aria-hidden="true">*</span>
                        </label>
                        <input type="email" name="email" class="form-control rounded-0" id="userEmail" aria-label="Adresse email" aria-required="true" required>
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                    </div>
                    <!-- CHAMPS DE MOT DE PASSE -->
                    <div class="mb-3">
                        <label for="password1" class="form-label">
                            Mot de passe <span aria-hidden="true">*</span>
                        </label>
                        <input type="password"
                               name="password1"
                               class="form-control rounded-0"
                               onkeyup="mdpverif()"
                               id="password1"
                               aria-label="Mot de passe"
                               aria-required="true"
                               required>
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                    </div>
                    <!-- CHAMPS DE CONFIRMATION DE MOT DE PASSE -->
                    <div class="mb-3">
                        <label for="password2" class="form-label">
                            Confirmation mot de passe <span aria-hidden="true">*</span>
                        </label>
                        <input type="password" name="password2" class="form-control rounded-0" onkeyup="mdpverif()" id="password2" aria-label="Confirmation de mot de passe" aria-required="true" required>
                        <span class="visually-hidden">Ce champ est obligatoire</span>
                    </div>
                    <div class="text-center">
                        <div id="mdpverif"></div>
                    </div>
                    <!-- CAPTCHA -->
                    <div class="cf-turnstile" data-sitekey="0x4AAAAAABCkJ2clzFt4U0Yt"></div>
                    <!-- BOUTON DE SUBMIT DU FORMULAIRE -->
                    <div class="text-center mt-4" id="boutonSiLeMDPestValide">

                    </div>
                    <!-- LIEN HYPERTEXTE REDIRECTION VERS LA PAGE DE CONNEXION -->
                    <div class="text-center mt-1">
                        <a href="${pageContext.request.contextPath}/login">J'ai déjà un compte</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

</main>
<%@include file="/WEB-INF/JSP/footer.jsp" %>
