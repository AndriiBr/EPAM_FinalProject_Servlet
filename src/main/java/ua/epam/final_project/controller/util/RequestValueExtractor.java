package ua.epam.final_project.controller.util;

public class RequestValueExtractor {

    public static String extractValueFromRequest(SessionRequestContent content, String key, String defaultValue) {
        String result;

        if (content.getReqParameters().get(key) != null) {
            result = content.getReqParameters().get(key);
        } else if (content.getReqAttributes().get(key) != null) {
            result = (String) content.getReqAttributes().get(key);
        } else {
            result = defaultValue;
        }

        if (result.equals("title")) {
            String lang = (String) content.getSessionAttributes().get("language");
            result = result.concat("_").concat(lang);
        }

        return result;
    }

    public static int extractValueFromRequest(SessionRequestContent content, String key, int defaultValue) {
        int result;

        if (content.getReqParameters().get(key) != null) {
            result = Integer.parseInt(content.getReqParameters().get(key));
        } else if (content.getReqAttributes().get(key) != null) {
            result = Integer.parseInt((String) content.getReqAttributes().get(key));
        } else {
            result = defaultValue;
        }

        return result;
    }
}
