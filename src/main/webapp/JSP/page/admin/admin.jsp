<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/JSP/header.jsp" %>
<main>
    <div class="container-fluid">
        <div class="row" style="height: 100vh; ">
            <c:if test="${menu == 'admin'}">
                <%@include file="/WEB-INF/JSP/menu-client/menu_admin.jsp" %>
            </c:if>
            <!-- COLONNE PRINCIPAL -->
            <!-- LISTE DES SIGNALEMENTS -->
            <div class="col-2 bloc-principal p-0 me-2">
                <form class="d-flex flex-column" action="${pageContext.request.contextPath}/admin" method="POST">
                    <input type="hidden" name="csrfToken" value="<c:out value='${csrfToken}'/>">
                    <input type="hidden" name="action" value="get">
                    <c:forEach var="signalements" items="${requestScope.signalementList}">
                        <button class=" btn btn-info d-flex  mt-2 p-2 mx-2" value="<c:out value='${signalements.messageId}'/>"
                                name="IdMessage">
                            Signalement de Message <c:out value="${signalements.messageId}"/>
                        </button>
                    </c:forEach>
                </form>
            </div>
            <!-- INFORMATION SUR LE SIGNALEMENT -->
            <div class="col p-0 bloc-principal me-2">
                <form class="d-flex flex-column" action="${pageContext.request.contextPath}/admin" method="POST">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col mt-3">
                                <div id="message">
                                    <c:if test="${not empty setDiv}">
                                        ${setDiv}
                                    </c:if>
                                </div>
                                <div class="row">
                                    <div class="col-4">
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th scope="col">Suspect</th>
                                                <th scope="col">Information</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>ID de l'utilisateur</td>
                                                <td>
                                                    <c:out value="${signalement.emetteurId}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Nom de l'utilisateur</td>
                                                <td>
                                                    <c:out value="${signalement.emetteurNom}"/>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th scope="col">Signalé par</th>
                                                <th scope="col">Information</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>ID de l'utilisateur</td>
                                                <td>
                                                    <c:out value="${signalement.utilisateurIdSignale}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Nom de l'utilisateur</td>
                                                <td>
                                                    <c:out value="${signalement.utilisateurNomSignale}"/>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>


                                    </div>
                                    <div class="col-4">
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th scope="col">#</th>
                                                <th scope="col">A saisir</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>Raison de signalement</td>
                                                <td>
                                                    <input
                                                            type="text"
                                                            name="raison"
                                                            id="raison"
                                                           value="<c:out value='${signalement.utilisateurNomSignale}'/>">
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="message d-flex justify-content-between mt-2 p-2 ">
                                    <div class="d-flex">
                                        <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
                                        <div class="ms-3">
                                            <div class="username"><c:out value="${signalement.emetteurNom}"/></div>
                                            <div class="messageUser"><c:out value="${signalement.messageTexte}"/></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="my-auto mt-3">
                                    <input type="hidden" name="csrfToken" value="<c:out value='${csrfToken}'/>">
                                    <input type="hidden" name="idUser" value="<c:out value="${signalement.emetteurId}"/>">
                                    <input type="hidden" name="IdMessage" value="<c:out value="${signalement.messageId}"/>">
                                    <button type="submit" name="action" class="btn btn-info" value="supprimer">
                                        Supprimer le signalement
                                    </button>
                                    <button type="submit" name="action" class="btn btn-warning" value="avertissement">
                                        Avertissement
                                    </button>
                                    <button type="submit" name="action" class="btn btn-danger" value="ban">
                                        Bannir ce utilisateur
                                    </button>
                                </div>

                            </div>
                        </div>
                    </div>

                </form>
            </div>
            <!-- INFORMATION SUR L'UTILISATEUR -->
            <div class="col-2 bloc-principal p-0 me-2">
                <div class="userGrand d-flex align-items-center p-2 m-1 d-flex justify-content-around">
                    <img src="/images/nightcity.jpg" alt="" class="avatarConversaGrand">
                    <div class="m-2">
                        <div class="usernameGrand">
                            <c:out value="${signalement.emetteurNom}"/>
                        </div>
                    </div>
                </div>
                <div class="message bg-white d-flex justify-content-between mt-2 p-2 mx-2">
                    <c:out value="${signalement.emetteurDateInscription}"/>
                </div>
            </div>


        </div>
    </div>
</main>
<%@include file="/WEB-INF/JSP/footer.jsp" %>