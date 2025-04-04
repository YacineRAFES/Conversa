// Fonction pour récupérer le token CSRF
export async function getCsrfToken(type) {
    try {
        const response = await fetch("http://localhost:8080/ConversaAPI_war/"+type, {
            method: "GET",
            credentials: "include"
        });

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération du CSRF token");
        }

        const data = await response.json();
        console.log("CSRF Token récupéré :", data.csrfToken);
        return data.csrfToken;
    } catch (error) {
        console.error("Erreur:", error);
        return null;
    }
}
