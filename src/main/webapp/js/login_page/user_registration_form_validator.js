//Length from 4 to 19 symbols
//Start with character
const LOGIN_PATTERN = /^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d._-]{4,19}$/;

//Minimum 8 symbols
//Minimum 1 uppercase and 1 lowercase letter
//Minimum 1 digit
const PASSWORD_PATTERN = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d\w\W]{8,}$/;

const EMAIL_PATTERN = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;


const form = document.querySelector("form"),
    loginField = form.querySelector(".login"),
    loginInput = loginField.querySelector("input"),

    emailField = form.querySelector(".email"),
    emailInput = emailField.querySelector("input"),

    passwordField = form.querySelector(".password"),
    passwordInput = passwordField.querySelector("input"),

    confirmPasswordField = form.querySelector(".password_confirm"),
    confirmPasswordInput = confirmPasswordField.querySelector("input");

//If user entered any character in login field this event will be triggered
loginInput.onkeyup = () => {
    //Call function to validate login
    checkLogin();
}

//If user entered any character in email field this event will be triggered
emailInput.onkeyup = () => {
    //Call function to validate email
    checkEmail();
}

//If user entered any character in password field this event will be triggered
passwordInput.onkeyup = () => {
    checkPassword();
}

//If user entered any character in confirm password field this event will be triggered
confirmPasswordInput.onkeyup = () => {
    checkConfirmPassword();
}

form.onsubmit = (e) => {
    //prevent form from default submitting
    e.preventDefault();

    //If login is empty
    if (loginInput.value === "") {
        loginField.classList.add("shake", "error");
    } else {
        //Function to validate login
        checkLogin();
    }

    //If email is empty
    if (emailInput.value === "") {
        emailField.classList.add("shake", "error");
    } else {
        //Function to validate email
        checkEmail();
    }

    //If password is empty
    if (passwordInput.value === "") {
        passwordField.classList.add("shake", "error");
    } else {
        //Function to validate password
        checkPassword();
    }

    //If confirmed password is empty
    if (confirmPasswordInput.value === "") {
        confirmPasswordField.classList.add("shake", "error");
    } else {
        //Function to validate password
        checkConfirmPassword();
    }

    //remove shale class after 500ms delay
    setTimeout(() => {
        loginField.classList.remove("shake");
        emailField.classList.remove("shake");
        passwordField.classList.remove("shake");
        confirmPasswordField.classList.remove("shake");
    }, 500);


    //If error class not contain errors (user entered valid credentials) so we can submit a form
    if (!loginField.classList.contains("error")
        && !emailField.classList.contains("error")
        && !passwordField.classList.contains("error")
        && !confirmPasswordField.classList.contains("error")) {
        form.submit();
    }
}

function checkLogin() {
    //if user input not match with pattern
    if (!loginInput.value.match(LOGIN_PATTERN)) {
        loginField.classList.add("error");
        let errorText = loginField.querySelector(".error-text");
        //if login is not empty then show message to write a valid login.
        //Else show message that login can`t be blank
        (loginInput.value !== "")
            ? errorText.innerText = "Login must begin with a symbol and contain minimum 5 characters"
            : errorText.innerText = "Login can`t be blank";
    } else {
        loginField.classList.remove("error");
    }
}

function checkEmail() {
    //if user input not match with pattern
    if (!emailInput.value.match(EMAIL_PATTERN)) {
        emailField.classList.add("error");
        let errorText = emailField.querySelector(".error-text");
        //if email is not empty then show message to write a valid email.
        //Else show message that email can`t be blank
        (emailInput.value !== "")
            ? errorText.innerText = "Please write correct email address"
            : errorText.innerText = "Email can`t be blank";
    } else {
        emailField.classList.remove("error");
    }
}

function checkPassword() {
    //if password is empty
    if (!passwordInput.value.match(PASSWORD_PATTERN)) {
        passwordField.classList.add("error");
        let errorText = passwordField.querySelector(".error-text");
        (passwordInput.value !== "")
            ? errorText.innerText = "Password must contain minimum 8 symbols, minimum one uppercase and lowercase letter, minimum one number"
            : errorText.innerText = "Password can`t be blank";
    } else {
        passwordField.classList.remove("error");
    }
}

function checkConfirmPassword() {
    //if confirmed password not equal to password
    if (confirmPasswordInput.value !== passwordInput.value) {
        confirmPasswordField.classList.add("error");
        let errorText = confirmPasswordField.querySelector(".error-text");
        (confirmPasswordInput.value !== "")
            ? errorText.innerText = "Passwords must be the same"
            : errorText.innerText = "Password can`t be blank";
    } else {
        confirmPasswordField.classList.remove("error");
    }
}
