package fr.afpa.dev.pompey.conversa.utilitaires;

public class Page {
    private Page() {}

    public static final class JSP{
        private JSP() {}

        /**
         * Page d'inscription
         * /JSP/page/register.jsp
         */
        public static final String REGISTER = "/JSP/page/register.jsp";

        /**
         * Page de connexion
         * /JSP/page/login.jsp
         */
        public static final String LOGIN = "/JSP/page/login.jsp";

        /**
         * Page d'accueil
         * /JSP/page/clients/home.jsp
         */
        public static final String HOME = "/JSP/page/clients/home.jsp";

        /**
         * Page de amis
         * /JSP/page/clients/amis.jsp
         */
        public static final String AMIS = "/JSP/page/clients/amis.jsp";

        /**
         * Page des messages privees
         * "/JSP/page/clients/messagesPrivee.jsp";
         */
        public static final String MESSAGES_PRIVEE = "/JSP/page/clients/messagesPrivee.jsp";

        /**
         * Page d'admin
         * /JSP/page/admin/admin.jsp
         */
        public static final String ADMIN = "/JSP/page/admin/admin.jsp";
    }

    public static final class URL {
        private URL() {}

        /**
         * URL de la page de création d'un compte
         * /register
         */
        public static final String REGISTER = "/register";

        /**
         * URL de la page de connexion d'un compte
         * /login
         */
        public static final String LOGIN = "/login";

        /**
         * URL de la page de l'accueil
         * /home
         */
        public static final String HOME = "/home";

        /**
         * URL de la page de la liste des amis
         * /amis
         */
        public static final String AMIS = "/amis";

        /**
         * URL de la page des messages privées
         * /messagesPrivee
         */
        public static final String MESSAGES_PRIVEE = "/messagesPrivee";

        /**
         * URL de la page d'administration
         * /admin
         */
        public static final String ADMIN = "/admin";

    }

}
