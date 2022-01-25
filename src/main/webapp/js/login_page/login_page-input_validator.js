//Length from 4 to 19 symbols
//Start with character
const LOGIN_PATTERN = /^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d._-]{4,19}$/;

//Minimum 8 symbols
//Minimum 1 uppercase and 1 lowercase letter
//Minimum 1 digit
const PASSWORD_PATTERN = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d\w\W]{8,}$/;

const EMAIL_PATTERN = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;


const form = document.querySelector("form"),
    eField = form.querySelector(".login"),
    eInput = eField.querySelector("input"),
    pField = form.querySelector(".password"),
    pInput = pField.querySelector("input");

form.onsubmit = (e) => {
    //prevent form from default submitting
    e.preventDefault();
    //If login is empty
    if (eInput.value === "") {
        eField.classList.add("shake", "error");
    } else {
        //Call function to validate login
        checkLogin();
    }
    //If password is empty
    if (pInput.value === "") {
        pField.classList.add("shake", "error");
    }

    //remove shale class after 500ms delay
    setTimeout(() => {
        eField.classList.remove("shake");
        pField.classList.remove("shake");
    }, 500);

    //If user entered any character in login field this event will be triggered
    eInput.onkeyup = () => {
        //Call function to validate login
        checkLogin();
    }

    //If user entered any character in password field this event will be triggered
    pInput.onkeyup = () => {
        checkPassword();
    }

    //If error class not contain errors (user entered valid credentials) so we can submit a form
    if (!eField.classList.contains("error") && !pField.classList.contains("error")) {
        form.submit();
    }
}

function checkLogin() {
    //if user input not match with pattern
    if (!eInput.value.match(LOGIN_PATTERN)) {
        eField.classList.add("error");
        let errorText = eField.querySelector(".error-text");
        //if login is not empty then show message to write a valid login.
        //Else show message that login can`t be blank
        (eInput.value !== "")
            ? errorText.innerText = "Login must begin with a symbol and contain minimum 4 characters"
            : errorText.innerText = "Login can`t be blank";
    } else {
        eField.classList.remove("error");
    }
}

function checkPassword() {
    //if password is empty
    if (!pInput.value.match(PASSWORD_PATTERN)) {
        pField.classList.add("error");
        let errorText = pField.querySelector(".error-text");
        (pInput.value !== "")
            ? errorText.innerText = "Password must contain minimum 8 symbols, minimum one uppercase and lowercase letter, minimum one number"
            : errorText.innerText = "Password can`t be blank";
    } else {
        pField.classList.remove("error");
    }
}
