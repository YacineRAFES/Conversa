<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/JSP/header.jsp" %>
<main>
    <div class="container-fluid">
        <div class="row" style="height: 100vh; ">
            <c:if test="${menu == 'admin'}">
                <%@include file="/WEB-INF/JSP/menu-client/menu_admin.jsp" %>
            </c:if>
            <c:if test="${menu == 'clients'}">
                <%@include file="/WEB-INF/JSP/menu-client/menu_client.jsp" %>
            </c:if>
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="bannière de la communauté">
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
                                    <img src="${pageContext.request.contextPath}/assets/images/nightcity.jpg" class="card-img-top" alt="...">
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
</main>
<%@include file="/WEB-INF/JSP/footer.jsp" %>