package fr.afpa.dev.pompey.conversa.utilitaires;

public enum AlertType {
    DANGER("alert-danger"),
    WARNING("alert-warning"),
    INFO("alert-info"),
    SUCCESS("alert-success");

    private final String cssClass;

    AlertType(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}
