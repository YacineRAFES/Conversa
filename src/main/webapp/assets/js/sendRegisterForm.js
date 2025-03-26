// Fonction pour envoyer les données avec le token CSRF
export async function sendFormData(formData) {
    console.log("FormData envoyé:", formData);
    try {
        const response = await fetch("http://localhost:8080/ConversaAPI_war/user", {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        });

        console.log("Utilisateur créé avec succès !");
    } catch (error) {
        console.error("Erreur:", error);
    }
}