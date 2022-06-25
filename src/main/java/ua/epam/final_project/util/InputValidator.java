package ua.epam.final_project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.controller.util.SessionRequestContent;

public class InputValidator {

    private static final Logger logger = LogManager.getLogger(InputValidator.class);

    private InputValidator() {}

    /**
     * Validate request values (String format)
     * @param content - Repository entity of all request parameters and attributes
     * @param key - vale key
     * @param defaultValue - default value if validation was unsuccessful or value was null
     * @return result of validation
     */
    public static String extractValueFromRequest(SessionRequestContent content, String key, String defaultValue) {
        String result;

        if (content.getReqParameters().get(key) != null) {
            result = content.getReqParameters().get(key);
        } else if (content.getReqAttributes().get(key) != null) {
            result = (String) content.getReqAttributes().get(key);
        } else {
            result = defaultValue;
        }

        if (key.equals("orderBy")) {
            if (!result.equals("title") && !result.equals("price")) {
                result = "price";
            } else if (result.equals("title")){
                String lang = (String) content.getSessionAttributes().get("language");
                if (!lang.equals("ua") && !lang.equals("en")) {
                    lang = "en";
                }
                result = result.concat("_").concat(lang);
            }
        }

        return result;
    }

    /**
     * Validate request values (int format)
     * @param content - Repository entity of all request parameters and attributes
     * @param key - vale key
     * @param defaultValue - default value if validation was unsuccessful or value was null
     * @return result of validation
     */
    public static int extractValueFromRequest(SessionRequestContent content, String key, int defaultValue) {
        int result;

        if (content.getReqParameters().get(key) != null) {
            try {
                result = Integer.parseInt(content.getReqParameters().get(key));
            } catch (NumberFormatException e) {
                result = defaultValue;
            }
        } else if (content.getReqAttributes().get(key) != null) {
            result = Integer.parseInt((String) content.getReqAttributes().get(key));
        } else {
            result = defaultValue;
        }

        return result;
    }

    /**
     * Validate user login form
     * @param login - User login
     * @param password - User password
     * @return result of validation
     */
    public static boolean validateLoginPassword(String login, String password) {
        return validateLogin(login) && validatePassword(password);
    }

    /**
     * Validate user registration form
     * @param login - User login
     * @param email - User email
     * @param password - User password
     * @param passwordConfirm - password confirmation
     * @return result of validation
     */
    public static boolean validateRegistrationForm(String login,
                                                   String email,
                                                   String password,
                                                   String passwordConfirm) {
        return validateLogin(login)
                && validatePassword(password)
                && validateEmail(email)
                && validateConfirmPassword(password, passwordConfirm);
    }

    /**
     * Validate input fields for edition
     * @param titleEn - English title
     * @param titleUa - Ukrainian title
     * @param textEn - English text
     * @param textUa - Ukrainian text
     * @param price - price
     * @param genre - genre
     * @return result of validation
     */
    public static boolean validateNewEdition(String titleEn,
                                             String titleUa,
                                             String textEn,
                                             String textUa,
                                             String price,
                                             String genre) {
        return validateTitleEn(titleEn)
                && validateTitleUa(titleUa)
                && validateText(textEn)
                && validateText(textUa)
                && validateMoney(price)
                && validateGenre(genre);
    }

    /**
     * Validate title_en input
     * @param titleEn - English title
     * @return validation result
     */
    private static boolean validateTitleEn(String titleEn) {
        String titleEnPattern = "^[A-Za-z0-9_ -|&?:]{2,50}$";
        return titleEn != null && titleEn.matches(titleEnPattern);
    }

    /**
     * Validate title_ua input
     * @param titleUa - English title
     * @return validation result
     */
    private static boolean validateTitleUa(String titleUa) {
        String titleUaPattern = "^[a-zA-Zа-яА-ЯіІйЙёЁ0-9 -]{2,50}$";
        return titleUa != null && titleUa.matches(titleUaPattern);
    }

    /**
     * Validate text input
     * @param text - text input
     * @return validation result
     */
    private static boolean validateText(String text) {
        return text != null && text.length() >= 2;
    }

    /**
     * Validate input to be a positive number
     * @param money - money input
     * @return validation result
     */
    public static boolean validateMoney(String money) {
        String moneyPattern = "^[1-9][\\d]*$";
        return money != null && money.matches(moneyPattern);
    }

    /**
     * Validate input to be a positive number
     * @param genre - genre id input
     * @return validation result
     */
    public static boolean validateGenre(String genre) {
        try {
            return genre != null && Integer.parseInt(genre) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate login input
     * @param login - user login
     * @return validation result
     */
    private static boolean validateLogin(String login) {
        return login != null && login.length() > 4;
    }

    /**
     * Validate password input
     * @param password - user password
     * @return validation result
     */
    private static boolean validatePassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\w\\W]{8,}$";
        return password != null && password.length() >= 8 && password.matches(passwordPattern);
    }

    /**
     * Compare password with password confirmation
     * @param password - user password
     * @param confirmPassword - password confirmation
     * @return validation result
     */
    private static boolean validateConfirmPassword(String password, String confirmPassword) {
        return validatePassword(password)
                && validatePassword(confirmPassword)
                && password.equals(confirmPassword);
    }

    /**
     * Validate email input
     * @param email - user email
     * @return validation result
     */
    private static boolean validateEmail(String email) {
        String emailPattern = "^[^ ]+@[^ ]+\\.[a-z]{2,4}$";
        return email != null && email.matches(emailPattern);
    }
}
