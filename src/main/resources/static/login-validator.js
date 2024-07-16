function validate() {
    var loginField = document.getElementById("login-field");
    var passwordField = document.getElementById("password-field");

    var regex = /[A-Za-z0-9]{4,}$/;

    if(!regex.test(loginField.value)) {
        return false;
    }

    if(!regex.test(passwordField.value)) {
        return false;
    }

    return true;
}