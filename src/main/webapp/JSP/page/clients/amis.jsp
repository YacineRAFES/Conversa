<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/JSP/header.jsp" %>
<main>
    <div class="container-fluid">
        <div class="row" style="height: 100vh; ">
            <%@include file="/WEB-INF/JSP/menu-client/menu_client.jsp" %>
            <!-- COLONNE PRINCIPAL -->
            <div class="col p-0 bloc-principal me-2">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col mt-3">
                            <!-- TITRE DE BIENVENUE -->
                            <div class="text-center fw-bold fs-4 text-white mb-3">
                                Liste des amis
                            </div>
                            <!-- FORMULAIRE DE RECHERCHE D'UN AMI OU AJOUT UN AMI -->
                            <form action="${pageContext.request.contextPath}/amis" method="post">
                                <div class="mb-3 d-flex justify-content-center">
                                    <input type="text" class="placeholderCustom form-control rounded-3"
                                           name="searchInput"
                                           style="width: 20%;"
                                           placeholder="Rechercher un ami ou saisir un utilisateur">
                                </div>

                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-primary px-4 mx-1" name="action" value="search">
                                        Rechercher un ami
                                    </button>
                                    <button type="submit" class="btn btn-primary px-4 mx-1" name="action" value="add">
                                        Ajouter un ami
                                    </button>
                                </div>
                            </form>
                            <!-- LISTE DES AMIS -->
                            <div class="d-flex d-inline-flex pt-3 flex-wrap overflow-y-scroll overflow-y-hidden" style="scrollbar-width: none;  height: calc(80vh - 1rem);">
                                <c:forEach var="ami" items="${amisList}">
                                    <div>
                                        <a class="user d-flex align-items-center p-2 m-1 px-3 text-decoration-none text-reset" href="#">
                                            <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
                                            <div class="m-2">
                                                <div class="username">${ami.username}</div>
                                            </div>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/JSP/footer.jsp" %>