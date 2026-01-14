package fr.afpa.dev.pompey.conversa.utilitaires;

public enum Alert {
        //Les erreurs de serveurs
        ERROR_SERVER("Erreur lors de la réception de la réponse du serveur.", AlertType.DANGER),

        //Les erreurs d'inscriptions
        USER_ALREADY_EXISTS("Le nom ou l'email existe déjà.", AlertType.WARNING),
        PASSWORD_INVALID("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre.", AlertType.WARNING),
        EMAIL_INVALID("L'adresse e-mail est invalide.", AlertType.WARNING),
        EMPTY_FIELD("Tous les champs doivent être remplis.", AlertType.WARNING),
        LENGTH_INVALID("Le nom d'utilisateur ou L'adresse e-mail ne doit pas contenir plus de 50 caractères.", AlertType.WARNING),
        ERROR_USER_CREATED("Erreur lors de la création du compte.", AlertType.WARNING),

        //Les erreurs de connexions
        INVALID_CREDENTIALS("Nom d'utilisateur ou mot de passe incorrect.", AlertType.WARNING),
        ERROR_CREDENTIALS("Erreur lors de la connexion du compte.", AlertType.WARNING),

        //Les erreurs d'amis
        FRIEND_REQUEST_SENT("Demande d'ami envoyée", AlertType.INFO),
        FRIEND_REQUEST_ALREADY_SENT("Demande d'ami déjà envoyée", AlertType.INFO),
        FRIEND_REQUEST_ALREADY_ACCEPTED("Demande d'ami déjà acceptée", AlertType.INFO),
        ACCEPT_FRIEND_REQUEST("Demande d'ami acceptée", AlertType.INFO),
        REFUSED_FRIEND_REQUEST("Demande d'ami refusée", AlertType.INFO),

        //Les erreurs d'admins
        USER_BAN("L'utilisateur est banni.", AlertType.WARNING),
        SIGNALEMENT_ISDELETE("Le signalement a été supprimé.", AlertType.WARNING),
        USER_WARNING("L'utilisateur a été averti.", AlertType.WARNING),
        USER_MODIFIED("Le compte de l'utilisateur a été modifié.", AlertType.WARNING),
        USER_DELETED("Le compte de l'utilisateur a été supprimé.", AlertType.WARNING),
        USER_CREATED("Votre compte a été crée. Veuillez de vous connecter.", AlertType.SUCCESS),

        //Autres erreurs
        UNKNOWN_ERROR("Erreur inconnue.", AlertType.WARNING),
        AUTHENTICATION_EXPIRED("Votre session a expiré. Veuillez vous reconnecter.", AlertType.WARNING),
        ERROR_FORM("Erreur du saisie.", AlertType.WARNING);

        private final String message;
        private final AlertType type;

        Alert(String message, AlertType type) {
            this.message = message;
            this.type = type;
        }

        public String toHtml() {
            return """
            <div class="alert %s alert-dismissible fade show text-center" role="alert">
                %s
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        """.formatted(type.getCssClass(), message);
        }
}
