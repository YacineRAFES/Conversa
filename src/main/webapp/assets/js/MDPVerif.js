//Verifie les mots de passe dans 2 inputs
export function mdpverif() {
    let mdp1 = document.getElementById("password1").value;
    let mdp2 = document.getElementById("password2").value;
    let pasok =
        '<div class="alert alert-warning" role="alert">Vérifiez que votre mot de passe correspond!</div>';
    let ok =
        '<div class="alert alert-success" role="alert">Votre mot de passe correspond bien.</div>';
    let button =
        '<button type="submit" value="Submit" class="btn btn-outline-success rounded-0 px-3">Créer un compte</button>';

    if (mdp1 == mdp2) {
        document.getElementById("mdpverif").innerHTML = ok;
        document.getElementById("boutonSiLeMDPestValide").innerHTML = button;
    } else {
        document.getElementById("mdpverif").innerHTML = pasok;
        document.getElementById("boutonSiLeMDPestValide").innerHTML = "";
    }
    if (mdp2 == "") {
        document.getElementById("mdpverif").innerHTML = "";
    }
    if (mdp1) {
        document.getElementById("password2").removeAttribute("disabled");
    } else {
        document.getElementById("password2").value = "";
        document.getElementById("password2").setAttribute("disabled", true);
        document.getElementById("boutonSiLeMDPestValide").innerHTML = "";
        document.getElementById("mdpverif").innerHTML = "";
    }
}
