// Fonction pour envoyer les données du formulaire
export async function loginUser(formData) {
    console.log("FormData envoyé:", formData);
    try {
        const response = await fetch("http://localhost:8080/ConversaAPI_war/login", {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        });
        const token = response.headers.get("Authorization");
        if (token?.startsWith("Bearer ")) {
            const jwt = token.split(" ")[1];
            sessionStorage.setItem("jwt", jwt);
            console.log("Token JWT stocké :", jwt);
        }
        return await response.json();
    } catch (error) {
        console.error("Erreur:", error);
    }
}