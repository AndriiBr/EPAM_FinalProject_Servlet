package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.*;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.exception.MultipartFormException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.util.InputValidator;
import ua.epam.final_project.util.MultipartExtractor;

import java.util.*;



public class SaveNewEditionCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(SaveNewEditionCommand.class);
    private static final String ERROR_UNKNOWN = "error.unknown";

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("admin.editions"));

        String titleEn;
        String titleUa;
        String textEn;
        String textUa;
        int price;
        int genre;
        String imgPath;

        try {
            Map<String, String> fieldMap = MultipartExtractor.extractMultipart(content);
            titleEn = fieldMap.get("title_en");
            titleUa = fieldMap.get("title_ua");
            textEn = fieldMap.get("text_en");
            textUa = fieldMap.get("text_ua");
            price = Integer.parseInt(fieldMap.get("price"));
            genre = Integer.parseInt(fieldMap.get("genre"));
            imgPath = MultipartExtractor.getAbsoluteImagePath(fieldMap.get("file-name"));
        } catch (MultipartFormException e) {
            logger.error(e);
            result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl(ERROR_UNKNOWN));
            return result;
        }

        boolean valid = InputValidator.validateNewEdition(titleEn, titleUa, textEn, textUa, price, genre);

        if (valid) {
            IEditionService editionService = ServiceFactory.getEditionService();
            Edition edition = new Edition(titleEn, titleUa, textEn, textUa, price, genre);
            edition.setImagePath(imgPath);

            boolean res = editionService.insertNewEdition(edition);

            if (!res) {
                result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl(ERROR_UNKNOWN));
                return result;
            }
        } else {
            result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl(ERROR_UNKNOWN));
            return result;
        }

        return result;
    }


    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.ADMIN);
    }

}
