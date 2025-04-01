<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:url value="/assets/js/script.js" var="script"/>--%>
<c:url value="/assets/js/bootstrap.js" var="bootstrapjs"/>
<c:url value="/assets/js/bootstrap.bundle.js" var="bootstrapbundlejs"/>
<footer>
    <c:if test="${title eq 'Inscription' or title eq 'Connexion'}">
        <script src="https://challenges.cloudflare.com/turnstile/v0/api.js" async defer></script>
    </c:if>
    <c:if test="${requestScope.title eq 'Inscription'}">
        <script type="module" src="/Conversa_war/assets/js/MDPVerif.js"></script>
    </c:if>

    <!-- Script de la page -->
    <script type="module" src="/Conversa_war/assets/js/${js}"></script>

<%--    <script type="module" src="${script}"></script>--%>
    <script src="${bootstrapbundlejs}"></script>
    <script src="${bootstrapjs}"></script>
</footer>
</body>
</html>
