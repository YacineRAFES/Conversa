package fr.afpa.dev.pompey.conversa.utilitaires;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();
    static {
        String PATHCONF = "conf.properties";
        try(InputStream input = Config.class.getClassLoader().getResourceAsStream(PATHCONF)) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Méthode pour récupérer la clé secrète pour le captcha.
     *
     * @return la clé secrète pour le captcha
     */
    public static String getSecretKeyCaptcha() {
        return props.getProperty("SECRET_KEY_CAPTCHA");
    }

    public static String getAPIURL(){
        return props.getProperty("API_REDIRECTION");
    }
}
