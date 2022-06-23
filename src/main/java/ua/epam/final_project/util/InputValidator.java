package ua.epam.final_project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.controller.util.SessionRequestContent;

public class InputValidator {

    private static final Logger logger = LogManager.getLogger(InputValidator.class);

    private InputValidator() {}

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

    public static boolean validateLoginPassword(String login, String email, String password, String passwordConfirm) {
        String emailRegex = "^[^ ]+@[^ ]+\\.[a-z]{2,4}$";

        if (login == null || email == null || password == null || passwordConfirm == null) {
            return false;
        } else if (login.equals("") || email.equals("") || password.equals("") || passwordConfirm.equals("")) {
            return false;
        } else if (login.length() < 4 || password.length() < 8) {
            return false;
        } else if (!password.equals(passwordConfirm)) {
            return false;
        } else return email.matches(emailRegex);
    }

    public static boolean validateNewEdition(String titleEn,
                                             String titleUa,
                                             String textEn,
                                             String textUa,
                                             int price,
                                             int genre) {
        String titleEnPattern = "^[A-Za-z0-9_ -|&?:]{2,50}$";
        String titleUaPattern = "^[a-zA-Zа-яА-ЯіІйЙёЁ0-9 -]{2,50}$";

        try {
            return  titleEn.matches(titleEnPattern)
                    && titleUa.matches(titleUaPattern)
                    && textEn.length() >= 2
                    && textUa.length() >= 2
                    && price > 0 && genre > 0;
        } catch (NullPointerException e) {
            logger.warn(e);
            return false;
        }
    }
}
