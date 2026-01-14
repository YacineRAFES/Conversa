<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/JSP/header.jsp" %>
<main>
    <div class="container-fluid">
        <div class="row" style="height: 100vh; ">
            <c:if test="${menu == 'admin'}">
                <%@include file="/WEB-INF/JSP/menu-client/menu_admin.jsp" %>
            </c:if>
            <!-- COLONNE PRINCIPAL -->
            <!-- LISTE DES UTILISATEURS -->
            <div class="col-2 bloc-principal p-0 me-2">
                <form class="d-flex flex-column" action="${pageContext.request.contextPath}/accmanagement" method="POST">
                    <input type="hidden" name="csrfToken" value="<c:out value='${csrfToken}'/>" >
                    <input type="hidden" name="action" value="get">
                    <c:forEach var="account" items="${requestScope.utilisateursList}">
                        <button class="btn btn-info d-flex mt-2 p-2 mx-2"
                                value="${account.userId}"
                                name="userId">
                            <c:out value="${account.userId}" /> - <c:out value="${account.userName}" />
                        </button>
                    </c:forEach>
                </form>
            </div>
            <!-- INFORMATION SUR L'UTILISATEUR -->
            <div class="col p-0 bloc-principal me-2">
                <form class="d-flex flex-column" action="${pageContext.request.contextPath}/accmanagement" method="POST">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col mt-3">
                                <div id="message">
                                    <c:if test="${not empty alert}">
                                        ${alert}
                                    </c:if>
                                </div>
                                <div class="row">
                                    <div class="col-4">
                                        <table class="table table-bordered">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Utilisateur</th>
                                                    <th scope="col">Information</th>
                                                </tr>
                                            </thead>
                                            <tbody class="table-group-divider">
                                                <tr>
                                                    <td>ID</td>
                                                    <td>
                                                        <c:out value='${utilisateur.userId}'/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Nom</td>
                                                    <td>
                                                        <input class="form-control" name="userName" type="text" value="<c:out value='${utilisateur.userName}'/>">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Email</td>
                                                    <td>
                                                        <input class="form-control" name="userEmail" type="text" value="<c:out value='${utilisateur.userEmail}'/>">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Date d'inscription</td>
                                                    <td>
                                                        <c:out value='${utilisateur.userDate}'/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Role</td>
                                                    <td>
                                                        <select class="form-select" name="userRole">
                                                            <option value="admin" <c:if test="${utilisateur.userRole == 'admin'}"> selected </c:if>>Admin</option>
                                                            <option value="user" <c:if test="${utilisateur.userRole == 'user'}"> selected </c:if>>Utilisateur</option>
                                                            <option value="user" <c:if test="${utilisateur.userRole == 'moderator'}"> selected </c:if>>Modérateur</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Statut du compte</td>
                                                    <td>
                                                        <select class="form-select" name="userValid">
                                                            <option value="true" <c:if test="${utilisateur.userIsValid == 'true'}"> selected </c:if>>Valide</option>
                                                            <option value="false" <c:if test="${utilisateur.userIsValid == 'user'}"> selected </c:if>>Fermé</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="col-4">
                                        <table class="table">
                                            <tbody>
                                                <tr>
                                                    <th colspan="2">Historiques de l'utilisateur</th>
                                                </tr>
                                                <tr>
                                                    <td>Aucun signalement précédent</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="my-auto mt-3">
                                    <input type="hidden" name="csrfToken" value="<c:out value='${csrfToken}'/>" >
                                    <input type="hidden" name="userId" value="<c:out value='${utilisateur.userId}'/>">
                                    <button type="submit" name="action" class="btn btn-warning" value="modif">
                                        Modifier
                                    </button>
                                    <button type="submit" name="action" class="btn btn-info" value="reset">
                                        Env. un mail de réinitialiser le MDP
                                    </button>
                                    <button type="submit" name="action" class="btn btn-danger" value="supprimer">
                                        Supprimer le compte
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
                        <div class="usernameGrand">${signalement.emetteurNom}</div>
                    </div>
                </div>
                <div class="message bg-white d-flex justify-content-between mt-2 p-2 mx-2">
                    ${signalement.emetteurDateInscription}
                </div>
            </div>


        </div>
    </div>
</main>
<%@include file="/WEB-INF/JSP/footer.jsp" %>