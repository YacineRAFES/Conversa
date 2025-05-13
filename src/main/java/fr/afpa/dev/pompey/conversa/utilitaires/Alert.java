package fr.afpa.dev.pompey.conversa.utilitaires;

public class Alert {
    private Alert() {}
    // ErrorServer = "Erreur lors de la réception de la réponse du serveur."
    private static final String ERROR_SERVER = "Erreur lors de la réception de la réponse du serveur.";

    // UserAlreadyExists = "Le nom ou l'email existe déjà."
    private static final String USER_ALREADY_EXISTS = "Le nom ou l'email existe déjà.";

    // passwordInvalid = 'Le mdp doit contenir ...'
    private static final String PASSWORD_INVALID = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre.";

    // EmailInvalid = "L'adresse e-mail est invalide."
    private static final String EMAIL_INVALID = "L'adresse e-mail est invalide.";

    // EmptyField = "Tous les champs doivent être remplis."
    private static final String EMPTY_FIELD = "Tous les champs doivent être remplis.";

    // LengthInvalid = "Le nom d'utilisateur ou L'adresse e-mail ne doit pas contenir plus de 50 caractères."
    private static final String LENGTH_INVALID = "Le nom d'utilisateur ou L'adresse e-mail ne doit pas contenir plus de 50 caractères.";

    // ErrorUserCreated = "Erreur lors de la création du compte."
    private static final String ERROR_USER_CREATED = "Erreur lors de la création du compte.";

    // InvalidCredentials = "Nom d'utilisateur ou mot de passe incorrect."
    private static final String INVALID_CREDENTIALS = "Nom d'utilisateur ou mot de passe incorrect.";

    // ErrorCredentials = "Erreur lors de la connexion du compte."
    private static final String ERROR_CREDENTIALS = "Erreur lors de la connexion du compte.";

    private static final String UNKNOWN_ERROR = "Erreur inconnue.";

    private static final String FRIEND_REQUEST_SENT = "Demande d'ami envoyée";

    private static final String FRIEND_REQUEST_ALREADY_SENT = "Demande d'ami déjà envoyée";

    private static final String FRIEND_REQUEST_ALREADY_ACCEPTED = "Demande d'ami déjà acceptée";

    private static final String ACCEPT_FRIEND_REQUEST = "Demande d'ami acceptée";

    private static final String REFUSED_FRIEND_REQUEST = "Demande d'ami refusée";

    private static final String AUTHENTICATION_EXPIRED = "Votre session a expiré. Veuillez vous reconnecter.";

    private static final String USER_BAN = "L'utilisateur est banni.";

    private static final String SIGNALEMENT_ISDELETE = "Le signalement a été supprimé.";

    private static final String USER_WARNING = "L'utilisateur a été averti.";

    private static final String USER_MODIFY = "Le compte de l'utilisateur a été modifié.";

    private static String setDivAlertDanger(String message) {
        return "<div class='alert alert-danger text-center' role='alert'>"+message+"</div>";
    }

    private static String setDivAlertWarning(String message) {
        return "<div class='alert alert-warning text-center' role='alert'>"+message+"</div>";
    }

    private static String setDivAlertInfo(String message) {
        return "<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">\n" +
                message +
                "  <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n" +
                "</div>";
    }

    /**
     * Avertissement pour les champs vides
     */
    public static final String EMPTYFIELD = setDivAlertWarning(EMPTY_FIELD);

    /**
     * Message d'erreur pour le serveur
     */
    public static final String ERRORSERVER = setDivAlertDanger(ERROR_SERVER);

    /**
     * Avertissement que l'utilisateur existe déjà
     */
    public static final String USERALREADYEXISTS = setDivAlertWarning(USER_ALREADY_EXISTS);

    /**
     * Avertissement pour le mot de passe invalide
     */
    public static final String PASSWORDINVALID = setDivAlertWarning(PASSWORD_INVALID);

    /**
     * Avertissement pour l'email invalide
     */
    public static final String EMAILINVALID = setDivAlertWarning(EMAIL_INVALID);

    /**
     * Avertissement pour la longueur invalide
     */
    public static final String LENGTHINVALID = setDivAlertWarning(LENGTH_INVALID);

    /**
     * Erreur pour la création de l'utilisateur
     */
    public static final String ERRORUSERCREATED =  setDivAlertDanger(ERROR_USER_CREATED);

    /**
     * Erreur pour les identifiants invalides
     */
    public static final String INVALIDCREDENTIALS = setDivAlertWarning(INVALID_CREDENTIALS);

    /**
     * Erreur pour les identifiants invalides
     */
    public static final String ERRORCREDENTIALS = setDivAlertDanger(ERROR_CREDENTIALS);

    /**
     * Erreur inconnue
     */
    public static final String UNKNOWNERROR = setDivAlertDanger(UNKNOWN_ERROR);

    /**
     * Information pour la demande d'ami envoyée
     */
    public static final String FRIENDREQUESTSENT = setDivAlertInfo(FRIEND_REQUEST_SENT);

    /**
     * Information pour la demande d'ami déjà envoyée
     */
    public static final String FRIENDREQUESTALREADYSENT = setDivAlertInfo(FRIEND_REQUEST_ALREADY_SENT);

    /**
     * Information pour la demande d'ami déjà acceptée
     */
    public static final String FRIENDREQUESTALREADYACCEPTED = setDivAlertInfo(FRIEND_REQUEST_ALREADY_ACCEPTED);

    /**
     * Information pour la demande d'ami acceptée
     */
    public static final String ACCEPTFRIENDREQUEST = setDivAlertInfo(ACCEPT_FRIEND_REQUEST);

    /**
     * Information pour la demande d'ami refusée
     */
    public static final String REFUSEDFRIENDREQUEST = setDivAlertInfo(REFUSED_FRIEND_REQUEST);

    /**
     * Information pour l'authentification expirée
     */
    public static final String AUTHENTICATIONEXPIRED = setDivAlertDanger(AUTHENTICATION_EXPIRED);

    /**
     * Information pour l'utilisateur est banni
     */
    public static final String USERBAN = setDivAlertInfo(USER_BAN);

    /**
     * Information pour le signalement a été supprimée
     */
    public static final String SIGNALEMENTISDELETE = setDivAlertInfo(SIGNALEMENT_ISDELETE);

    /**
     * Information pour l'avertissement d'un utilisateur
     */
    public static final String USERWARNING = setDivAlertInfo(USER_WARNING);

    /**
     * Information pour la modification du compte d'un utilisateur
     */
    public static final String USERMODIFY = setDivAlertInfo(USER_MODIFY);
}
