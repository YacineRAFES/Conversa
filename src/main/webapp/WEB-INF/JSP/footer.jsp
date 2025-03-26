<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/assets/js/script.js" var="script"/>
<c:url value="/assets/js/bootstrap.js" var="bootstrapjs"/>
<c:url value="/assets/js/bootstrap.bundle.js" var="bootstrapbundlejs"/>
<c:url value="/assets/js/MDPVerif.js" var="mdpverif"/>
<footer>

    <script type="module">
        import { mdpverif } from '${mdpverif}';
        window.mdpverif = mdpverif;
    </script>
    <script type="module" src="${script}"></script>
    <script src="${bootstrapbundlejs}"></script>
    <script src="${bootstrapjs}"></script>
</footer>
</body>
</html>
