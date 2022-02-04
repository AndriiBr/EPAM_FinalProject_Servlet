//Allows only positive integers that are greater than 0.
// Easily modified for +/- integers and allowing zero.
const MONEY_VALUE_PATTERN = /^[1-9]+\d*$/;

function checkMoney() {
    //if user input not match with pattern
    if (!moneyInput.value.match(MONEY_VALUE_PATTERN)) {
        moneyField.classList.add("error");
        let errorText = moneyField.querySelector(".error-text");
        //if money value is not empty then show message to write a valid number.
        //Else show message that money value can`t be blank
        (moneyInput.value !== "")
            ? errorText.innerText = "Amount must be a positive number greater than 0"
            : errorText.innerText = "Can`t be blank";
    } else {
        moneyField.classList.remove("error");
    }
}