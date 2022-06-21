package ua.epam.final_project.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Hold set of page configurations to be executed by command
 */
public class ExecutionResult {

    private String page;
    private String redirectUrl;
    private Direction direction;
    private boolean isInvalidated;
    private final Map<String, Object> requestAttributes;
    private final Map<String, String> requestParameters;
    private final Map<String, Object> sessionAttributes;

    public ExecutionResult(SessionRequestContent content) {
        requestAttributes = content.getReqAttributes();
        requestParameters = content.getReqParameters();
        sessionAttributes = content.getSessionAttributes();
        isInvalidated = false;
    }

    public void addRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public void addRequestParameter(String key, String value) {
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
                redirectUrl = redirectUrl.concat(key).concat("=").concat(value).concat("&"));
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
