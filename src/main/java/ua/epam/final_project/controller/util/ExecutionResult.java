package ua.epam.final_project.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Hold set of page configurations to be executed by command
 */
public class ExecutionResult {

    private String page;
    private String redirectUrl;
    private Direction direction;
    private boolean isInvalidated;
    private final Map<String, Object> requestAttributes = new HashMap<>();
    private final Map<String, Object> requestParameters = new HashMap<>();
    private final Map<String, Object> sessionAttributes = new HashMap<>();

    public void addRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public void addRequestParameter(String key, Object value) {
        requestParameters.put(key, value);
    }

    public void addSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

    public void updateRequest(HttpServletRequest req) {
        requestAttributes.forEach(req::setAttribute);
        sessionAttributes.forEach(req.getSession()::setAttribute);
    }

    public void addUrlParameters() {
        requestParameters.forEach((key, value) ->
                page = page.concat(key).concat("=").concat((String) value).concat("&"));
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isInvalidated() {
        return isInvalidated;
    }

    public void invalidateSession() {
        isInvalidated = true;
    }
}
