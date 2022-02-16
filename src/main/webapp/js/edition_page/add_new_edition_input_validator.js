const form = document.querySelector("form[name='new_edition_form']"),
    titleEnField = form.querySelector(".titleEn"),
    titleEnInput = titleEnField.querySelector("input[name='titleEn']"),

    titleUaField = form.querySelector(".titleUa"),
    titleUaInput = titleUaField.querySelector("input[name='titleUa']"),

    priceField = form.querySelector(".price"),
    priceInput = priceField.querySelector("input[name='price']"),

    genreField = form.querySelector(".genre"),
    genreInput = genreField.querySelector("select[name='genre']");


//If user entered any character in title field this event will be triggered
titleEnInput.onkeyup = () => {
    checkTitleEn();
}

titleUaInput.onkeyup = () => {
    checkTitleUa();
}

//If user entered any character in email field this event will be triggered
priceInput.onkeyup = () => {
    checkPrice();
}

//If user entered any character in password field this event will be triggered
genreInput.onchange = () => {
    checkGenre();
}


form.onsubmit = (e) => {
    //prevent form from default submitting
    e.preventDefault();

    // If title is empty (EN)
    if (titleEnInput.value === "") {
        titleEnField.classList.add("shake", "error");
    } else {
        //Function to validate title
        checkTitleEn();
    }

    // If title is empty (UA)
    if (titleUaInput.value === "") {
        titleUaField.classList.add("shake", "error");
    } else {
        //Function to validate title
        checkTitleUa();
    }

    //If price is empty
    if (priceInput.value === "") {
        priceField.classList.add("shake", "error");
    } else {
        //Function to validate price
        checkPrice();
    }

    //If genre is empty
    if (genreInput.value === "Choose a genre") {
        genreField.classList.add("shake", "error");
    } else {
        //Function to validate genre
        checkGenre();
    }


    //remove shale class after 500ms delay
    setTimeout(() => {
        titleEnField.classList.remove("shake");
        titleUaField.classList.remove("shake");
        priceField.classList.remove("shake");
        genreField.classList.remove("shake");
    }, 500);


    //If error class not contain errors (user entered valid inputs) so we can submit a form
    if (!titleEnField.classList.contains("error")
        && !titleUaField.classList.contains("error")
        && !priceField.classList.contains("error")
        && !genreField.classList.contains("error")) {
        form.submit();
    }
}