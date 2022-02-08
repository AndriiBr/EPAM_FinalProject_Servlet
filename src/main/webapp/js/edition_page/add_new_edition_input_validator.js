const form = document.querySelector("form"),
    titleField = form.querySelector(".title"),
    titleInput = titleField.querySelector("input"),

    priceField = form.querySelector(".price"),
    priceInput = priceField.querySelector("input"),

    genreField = form.querySelector(".genre"),
    genreInput = genreField.querySelector("select");


//If user entered any character in title field this event will be triggered
titleInput.onkeyup = () => {
    checkTitle();
}

//If user entered any character in email field this event will be triggered
priceInput.onkeyup = () => {
    checkPrice();
}

//If user entered any character in password field this event will be triggered
genreInput.onkeyup = () => {
    checkGenre();
}


form.onsubmit = (e) => {
    //prevent form from default submitting
    e.preventDefault();

    // If title is empty
    if (titleInput.value === "") {
        titleField.classList.add("shake", "error");
    } else {
        //Function to validate title
        checkTitle();
    }

    //If price is empty
    if (priceInput.value === "") {
        priceField.classList.add("shake", "error");
    } else {
        //Function to validate price
        checkPrice();
    }

    //If genre is empty
    if (genreInput.value === "" || genreInput.value === "Choose a genre") {
        genreField.classList.add("shake", "error");
    } else {
        //Function to validate genre
        checkGenre();
    }


    //remove shale class after 500ms delay
    setTimeout(() => {
        titleField.classList.remove("shake");
        priceField.classList.remove("shake");
        genreField.classList.remove("shake");
    }, 500);


    //If error class not contain errors (user entered valid inputs) so we can submit a form
    if (!titleField.classList.contains("error")
        && !priceField.classList.contains("error")
        && !genreField.classList.contains("error")) {
        form.submit();
    }
}