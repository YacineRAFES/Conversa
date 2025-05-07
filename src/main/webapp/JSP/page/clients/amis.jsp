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
                            <div id="message">
                                <c:if test="${not empty setDiv}">
                                    ${setDiv}
                                </c:if>
                            </div>

                            <!-- TITRE DE BIENVENUE -->
                            <div class="text-center fw-bold fs-4 text-white mb-3">
                                Liste des amis
                            </div>
                            <!-- BARRE DE RECHERCHE D'UN AMI OU AJOUTER UN AMI -->
                            <form action="${pageContext.request.contextPath}/amis" method="post">
                                <input type="hidden" name="csrfToken" value="${csrfToken}"/>
                                <input type="hidden" name="formType" value="friendSearchForm">
                                <div class="mb-3 d-flex justify-content-center">
                                    <input type="text" class="placeholderCustom form-control rounded-3"
                                           name="username"
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
            <div class="col-2 bloc-principal pt-3">
                <div class="text-center fw-bold fs-4 text-white mb-3">
                    Demande d'amis
                </div>
                <!-- LISTE DES DEMANDES D'AMIS -->
                <c:forEach var="amisRequest" items="${amisRequest}">
                    <div class="user-friendrequest d-flex align-items-center p-2 m-1 text-decoration-none text-reset">
                        <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
                        <div class="m-2">
                            <div class="username">${amisRequest.username}</div>
                        </div>
                        <div class="ms-auto">
                            <form action="${pageContext.request.contextPath}/amis" class="" method="post">
                                <input name="id" type="hidden" value="${amisRequest.idGroupeMessagesPrives}">

                                <input type="hidden" name="formType" value="friendRequestResponse">
                                <!-- CSRF TOKEN -->
                                <input type="hidden" name="csrfToken" value="${csrfToken}"/>

                                <input name="username" type="hidden" value="${amisRequest.username}">
                                <!-- Bouton YES pour accepter la demande d'amis -->
                                <button type="submit" class="btn-friendrequest-valider" name="action" value="yes">
                                    <i class="bi bi-check-lg"></i>
                                </button>
                                <!-- Bouton NO pour refuser la demande d'amis -->
                                <button type="submit" class="btn-friendrequest-annuler" name="action" value="no">
                                    <i class="bi bi-x-lg"></i>
                                </button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/JSP/footer.jsp" %>