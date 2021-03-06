package ua.epam.final_project.controller.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * A Repository of session and request attributes and parameters
 */
public class SessionRequestContent {

    private final Map<String, Object> reqAttributes;
    private final Map<String, String> reqParameters;
    private final Map<String, Object> sessionAttributes;
    private final HttpServletRequest req;

    /**
     * Constructor extract attributes and parameters from provided HttpServletRequest
     * @param req - HttpServletRequest
     */
    public SessionRequestContent(HttpServletRequest req) {
        reqAttributes = extractRequestAttributes(req);
        reqParameters = extractRequestParameters(req);
        sessionAttributes = extractSessionAttributes(req);
        this.req = req;
    }

    /**
     * Extract request parameters from current request
     * @param req - HttpServletRequest
     * @return a map with request parameters.
     */
    private Map<String, String> extractRequestParameters(HttpServletRequest req) {
        Map<String, String> buffer = new HashMap<>();
        req.getParameterMap().forEach((key, val) -> buffer.put(key, val[0]));

        return buffer;
    }

    /**
     * Extract request attributes from current request
     * @param req - HttpServletRequest
     * @return a map with request attributes.
     */
    private Map<String, Object> extractRequestAttributes(HttpServletRequest req) {
        Map<String, Object> requestAttributes = new HashMap<>();
        Enumeration<String> requestAttributeNames = req.getAttributeNames();

        while (requestAttributeNames.hasMoreElements()) {
            String attributeName = requestAttributeNames.nextElement();
            requestAttributes.put(attributeName, req.getAttribute(attributeName));
        }

        return requestAttributes;
    }

    /**
     * Extract session attributes from current session
     * @param req - HttpServletRequest
     * @return a map with session attributes. Or an empty map if no session exists.
     */
    private Map<String, Object> extractSessionAttributes(HttpServletRequest req) {
        Map<String, Object> attributes = new HashMap<>();
        HttpSession session = req.getSession(false);

        if (session != null) {
            Enumeration<String> sessionAttributeNames = session.getAttributeNames();

            while (sessionAttributeNames.hasMoreElements()) {
                String attributeName = sessionAttributeNames.nextElement();
                attributes.put(attributeName, session.getAttribute(attributeName));
            }
        }
        return attributes;
    }

    public Map<String, Object> getReqAttributes() {
        return reqAttributes;
    }

    public Map<String, String> getReqParameters() {
        return reqParameters;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public HttpServletRequest getReq() {
        return req;
    }
}
