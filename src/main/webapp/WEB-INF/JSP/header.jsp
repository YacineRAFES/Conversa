<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/assets/css/bootstrap.css" var="bootstrap"/>
<c:url value="/assets/css/style.css" var="style"/>
<c:url value="/assets/css/bootstrapicons.css" var="bootstrapicons"/>
<c:url value="/assets/js/script.js" var="script"/>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title} - Conversa</title>
    <link rel="stylesheet" href="${bootstrap}">
    <link rel="stylesheet" href="${style}">
    <link rel="stylesheet" href="${bootstrapicons}">
    <c:if test="${requestScope.title eq 'Inscription' or requestScope.title eq 'Connexion'}">
        <script src="https://challenges.cloudflare.com/turnstile/v0/api.js" async defer></script>
    </c:if>
</head>
<body class="min-vh-100 bg-gradient-custom">
