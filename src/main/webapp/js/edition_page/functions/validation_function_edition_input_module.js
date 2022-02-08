//Length from 4 to 50 symbols
//Start with character
const TITLE_PATTERN = /^[A-Za-z0-9_ -|&?:]{2,50}$/;
//Only positive digits
//Cannot start with 0
const PRICE_PATTERN = /^[1-9][\d]*$/;

function checkTitle() {
    //if user input not match with pattern
    if (!titleInput.value.match(TITLE_PATTERN)) {
        titleField.classList.add("error");
        let errorText = titleField.querySelector(".error-text");
        //if title is not empty then show message to write a valid title.
        //Else show message that title can`t be blank
        (titleInput.value !== "")
            ? errorText.innerText = "Title must begin with a symbol and contain minimum 2 characters"
            : errorText.innerText = "Title can`t be blank";
    } else {
        titleField.classList.remove("error");
    }
}

function checkPrice() {
    //if price is empty
    if (!priceInput.value.match(PRICE_PATTERN)) {
        priceField.classList.add("error");
        let errorText = priceField.querySelector(".error-text");
        (priceInput.value !== "")
            ? errorText.innerText = "Price should be a positive number"
            : errorText.innerText = "Price can`t be blank";
    } else {
        priceField.classList.remove("error");
    }
}

function checkGenre() {
    //if price is empty
    if (genreInput.value === "Choose a genre") {
        genreField.classList.add("error");
    } else {
        genreField.classList.remove("error");
    }
}


