<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<c:url value="/assets/js/script.js" var="script"/>--%>
<c:url value="/assets/js/bootstrap.js" var="bootstrapjs"/>
<c:url value="/assets/js/bootstrap.bundle.js" var="bootstrapbundlejs"/>
<footer>
    <c:if test="${requestScope.title eq 'Inscription' or requestScope.title eq 'Connexion'}">
        <script src="https://challenges.cloudflare.com/turnstile/v0/api.js" async defer></script>
    </c:if>
    <%-- TODO: A REVOIR --%>
    <c:if test="${requestScope.title eq 'Inscription'}">
        <script src="${pageContext.request.contextPath}/assets/js/MDPVerif.js"></script>
    </c:if >
    <c:if test="${js eq 'messagesprivee.js'}">
        <script src="${pageContext.request.contextPath}/assets/js/messagesprivee.js" type="module"></script>
    </c:if >


<%--    <script type="module" src="${script}"></script>--%>
    <script src="${bootstrapbundlejs}"></script>
    <script src="${bootstrapjs}"></script>
</footer>
</body>
</html>
