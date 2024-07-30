function validateRegister() {
    var loginField = document.getElementById("login-field");
    var passwordField = document.getElementById("password-field");
    var confirmPasswordField = document.getElementById("confirm-password-field");
    var nameField = document.getElementById("name-field");
    var surnameField = document.getElementById("surname-field");

    var regex = /[A-Za-z0-9]{4,}$/;
    var nameRegex = /^[A-Z][a-zA-Z]{2,}$/;
    let counter = 0;

    document.getElementById("login-message").innerHTML = "";
    document.getElementById("password-message").innerHTML = "";
    document.getElementById("confirm-password-message").innerHTML = "";
    document.getElementById("name-message").innerHTML = "";
    document.getElementById("surname-message").innerHTML = "";

    if(!regex.test(loginField.value)) {
        document.getElementById("login-message").innerHTML = "Bad login. Login must contain 4 letters or numbers"
        counter++;
    }

    if(!regex.test(passwordField.value)) {
        document.getElementById("password-message").innerHTML = "Bad password. Password must contain 4 letters or numbers"
        counter++;
    }

    if(passwordField.value !== confirmPasswordField.value) {
        document.getElementById("confirm-password-message").innerHTML = "Passwords do not match."
        counter++;
    }

    if(!nameRegex.test(nameField.value)) {
        document.getElementById("name-message").innerHTML = "Bad name. Name must contain at least 3 letters and start with capital letter."
        counter++;
    }

    if(!nameRegex.test(surnameField.value)) {
        document.getElementById("surname-message").innerHTML = "Bad surname. Surname must contain at least 3 letters and start with capital letter."
        counter++;
    }

    return counter <= 0;


}