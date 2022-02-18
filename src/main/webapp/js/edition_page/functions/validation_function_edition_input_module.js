//Length from 4 to 50 symbols
//Start with character
const TITLE_PATTERN_EN = /^[A-Za-z0-9_ -|&?:]{2,50}$/;
const TITLE_PATTERN_UA = /^[a-zA-Zа-яА-Яё0-9 -]{2,50}$/;

//Only positive digits
//Cannot start with 0
const PRICE_PATTERN = /^[1-9][\d]*$/;

function checkTitleEn() {
    //if user input not match with pattern
    if (!titleEnInput.value.match(TITLE_PATTERN_EN)) {
        titleEnField.classList.add("error");
        let errorText = titleEnField.querySelector(".error-text");
        //if title is not empty then show message to write a valid title.
        //Else show message that title can`t be blank
        (titleEnInput.value !== "")
            ? errorText.innerText = "Title must begin with a symbol and contain minimum 2 characters"
            : errorText.innerText = "Title can`t be blank";
    } else {
        titleEnField.classList.remove("error");
    }
}

function checkTitleUa() {
    //if user input not match with pattern
    if (!titleUaInput.value.match(TITLE_PATTERN_UA)) {
        titleUaField.classList.add("error");
        let errorText = titleUaField.querySelector(".error-text");
        //if title is not empty then show message to write a valid title.
        //Else show message that title can`t be blank
        (titleUaInput.value !== "")
            ? errorText.innerText = "Title must begin with a symbol and contain minimum 3 characters"
            : errorText.innerText = "Title can`t be blank";
    } else {
        titleUaField.classList.remove("error");
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
    if (genreInput.value === "Genre" || genreInput.value === "Жанр") {
        genreField.classList.add("error");
    } else {
        genreField.classList.remove("error");
    }
}


