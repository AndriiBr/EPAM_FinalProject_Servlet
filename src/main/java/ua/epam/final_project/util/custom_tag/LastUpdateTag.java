package ua.epam.final_project.util.custom_tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LastUpdateTag extends TagSupport {

    private static final Logger logger = LogManager.getLogger(LastUpdateTag.class);

    @Override
    public int doStartTag() {
        JspWriter writer = pageContext.getOut();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formatDateTime = now.format(formatter);

        try {
            writer.print(formatDateTime);
        } catch (IOException e) {
            logger.warn(e);
        }

        return SKIP_BODY;
    }
}
