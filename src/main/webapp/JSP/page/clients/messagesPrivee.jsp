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
                <div class="col p-0">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-2 p-2 mt-2 bloc-principal">
                                <!-- TODO: message privée à faire -->
                                <!-- FORM -->
                                <form action="" class="m-1 mb-2" method="post">
                                    <input type="text" class="form-control placeholderCustom" name="" id="searchFriendMP"
                                           placeholder="Rechercher une conversation...">
                                </form>
                                <div id="listAllMessagesOfUser" class="listAllMessagesOfUserClass overflow-y-scroll overflow-y-hidden mt-0">
                                    <div  class="user d-flex align-items-center p-2 m-1">
                                        <img src="assets/images/nightcity.jpg" alt="" class="avatarConversa">
                                        <div class="m-2">
                                            <div  class="username">Username</div>
                                            <div class="messageUserRecent">Hello World!</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- PARTIE PRINCIPAL (LISTE DES MESSAGES ET UN BLOC POUR ECRIRE UN MESSAGE) -->
                            <div class="col p-0 ms-2 justify-content-between">
                                <!-- LISTE DES MESSAGES -->
                                <div class="listOfMessage overflow-y-scroll overflow-y-hidden mt-0 pb-2" id="listeOfMessage">
                                    <div class="message d-flex justify-content-between mt-2 p-2">
                                        <div class="d-flex">
                                            <img src="assets/images/nightcity.jpg" alt=""
                                                 class="avatarConversa">
                                            <div class="ms-3">
                                                <div class="username">Username</div>
                                                <div class="messageUser">Hello World!</div>
                                            </div>
                                        </div>
                                        <div class="my-auto mx-3 rounded-circle OptionsMessage">
                                            <button class="mainmenubtn boutonOptionMessage" onclick="Supprimer()">
                                                <i class="bi bi-x-lg fs-4 fw-bold"></i>
                                            </button>
                                            <button class="mainmenubtn boutonOptionMessage" onclick="Signaler()">
                                                <i class="bi bi-flag fs-4 fw-bold"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <!-- PARTIE POUR ECRIRE UN MESSAGE -->
                                <div class="insertMessage">
                                    <textarea class="textareaCustom autoResize" name="" id="Msg"></textarea>
                                    <input type="hidden" name="csrfToken" id="csrfToken" value="<c:out value='${csrfToken}'/>">
                                    <input type="hidden" name="idGrpMsgPrivee" id="idGrpMsgPrivee" value="<c:out value='${idGrpMsgPrivee}'/>">
                                    <input type="hidden" id="currentUserId" value="<c:out value='${user.id}'/>">
                                    <!-- <label for="fichiers"
                                               class="custom-file-upload d-flex align-items-center rounded-circle m-auto"><i
                                                class="bi bi-file-image"></i></label>
                                        <input type="file" name="fichier" id="fichiers"> -->
                                    <button type="submit" class="button-mp" id="sendMsg">
                                        <i class="bi bi-send"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="col-3 bloc-principal ms-2 p-0 mb-0 me-2 z-0 z-indexlow position-relative">
                                fdsfs
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
<%@include file="/WEB-INF/JSP/footer.jsp" %>