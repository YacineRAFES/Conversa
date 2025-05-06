<%@include file="/WEB-INF/JSP/header.jsp" %>
<main>
    <div class="container-fluid">
        <div class="row" style="height: 100vh; ">
            <c:if test="${menu == 'admin'}">
                <%@include file="/WEB-INF/JSP/menu-client/menu_admin.jsp" %>
            </c:if>
            <c:if test="${menu == 'user'}">
                <%@include file="/WEB-INF/JSP/menu-client/menu_client.jsp" %>
            </c:if>
<%--            TODO: A FAIRE POUR TOUT LES PAGES--%>
                    <!-- COLONNE PRINCIPAL -->

                <div class="col-2 bloc-principal p-0 me-2">
                    <form class="d-flex flex-column" action="" method="post">
                        <button class=" btn btn-info d-flex  mt-2 p-2 mx-2" value="numérodemessage" name="idMessage">
                            Signalement de Message 1111
                        </button>
                    </form>
                </div>
                    <div class="col p-0 bloc-principal me-2">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col mt-3">
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
                                                        <td>IDUSER</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Nom de l'utilisateur</td>
                                                        <td>USER_NAME</td>
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
                                                        <td>IDUSER</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Nom de l'utilisateur</td>
                                                        <td>USER_NAME</td>
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
                                                            <input type="text" name="raison" id="raison">
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="col-4">
                                            <table class="table">
                                                <tbody>
                                                    <tr>
                                                        <td colspan="2">Historiques de l'utilisateur</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Aucun signalement précédent</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="listOfMessage overflow-y-scroll overflow-y-hidden mt-0 p-3"
                                         style="scrollbar-width: none;" id="listeOfMessage">

                                        <div class="message d-flex justify-content-between mt-2 p-2 ">
                                            <div class="d-flex">
                                                <img src="/images/nightcity.jpg" alt="" class="avatarConversa">
                                                <div class="ms-3">
                                                    <div class="username">Username</div>
                                                    <div class="messageUser">Hello World!</div>
                                                </div>
                                            </div>
                                            <div class="my-auto mx-3 rounded-circle OptionsMessage">
                                                <button class="mainmenubtn boutonOptionMessage" href="">
                                                    <i class="bi bi-x-lg fs-4 fw-bold"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="my-auto mx-3">
                                        <form action="/admin" method="POST">
                                            <input type="hidden" name="csrfToken" value="#">
                                            <input type="hidden" name="idUser" value="#">
                                            <input type="hidden" name="idMessage" value="#">
                                            <button type="button" class="btn btn-info">
                                                Supprimer le signalement
                                            </button>
                                            <button type="button" class="btn btn-warning">
                                                Avertissement
                                            </button>
                                            <button type="button" class="btn btn-danger">
                                                Bannir ce utilisateur
                                            </button>

                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-2 bloc-principal p-0 me-2">
                        <div class="userGrand d-flex align-items-center p-2 m-1 d-flex justify-content-around">
                            <img src="/images/nightcity.jpg" alt="" class="avatarConversaGrand">
                            <div class="m-2">
                                <div class="usernameGrand">Username</div>
                            </div>
                        </div>
                        <div class="message bg-white d-flex justify-content-between mt-2 p-2 mx-2">
                            Inscrit depuis 19 sept 2019
                        </div>
                    </div>


                </div>
            </div>
        </main>
<%@include file="/WEB-INF/JSP/footer.jsp" %>