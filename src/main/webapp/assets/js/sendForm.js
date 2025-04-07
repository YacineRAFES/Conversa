// Fonction pour envoyer les données du formulaire
export async function sendFormData(formData, type) {
    console.log("FormData envoyé:", formData);
    try {
        const response = await fetch("http://localhost:8080/ConversaAPI_war/"+type, {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        });
        return await response.json(); // Retourne la réponse JSON
    } catch (error) {
        console.error("Erreur:", error);
    }
}