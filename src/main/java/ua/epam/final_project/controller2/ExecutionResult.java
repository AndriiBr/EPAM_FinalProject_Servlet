package ua.epam.final_project.controller2;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *  This class is a repository containing all attributes and parameters of the current session.
 *  An instance of this class is created each time a new command from jsp page arrives at CommandServlet.
 *  Each individual command class is responsible for populating an instance of this class with data.
 */
public class ExecutionResult {

    private String page;
    private String url;
    private Direction direction;
    private boolean isInvalidated;
    private final Map<String, Object> sessionAttributes;
    private final Map<String, Object> requestAttributes;
    private final Map<String, Object> requestParameters;

    public ExecutionResult() {
        sessionAttributes = new HashMap<>();
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
    }

    public void addRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public void addSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

    public void addRequestParameter(String key, Object value) {
        requestParameters.put(key, value);
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isInvalidated() {
        return isInvalidated;
    }

    public void updateRequest(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach(request.getSession()::setAttribute);
    }

    public void addParametersToPage() {
        requestParameters.forEach((String key, Object value) ->
                page = page.concat(key).concat("=").concat((String) value).concat("&"));
    }

    public void invalidateSession() {
        isInvalidated = true;
    }
}
