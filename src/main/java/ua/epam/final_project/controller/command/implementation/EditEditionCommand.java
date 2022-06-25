package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.exception.MultipartFormException;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.util.DeleteImageFromExternalDirectory;
import ua.epam.final_project.util.InputValidator;
import ua.epam.final_project.util.MultipartExtractor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EditEditionCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(EditEditionCommand.class);
    private final IEditionService editionService;

    public EditEditionCommand() {
        this.editionService = ServiceFactory.getEditionService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("error.unknown"));

        int editEditionId = InputValidator.extractValueFromRequest(content, "edit_edition_id", -1);

        try {
            Edition edition = editionService.findEditionById(editEditionId);

            String oldImgPath = edition.getImagePath();
            edition = modifyEdition(edition, content);

            if(edition != null) {
                if (edition.getImagePath().equals("no image")) {
                    edition.setImagePath(oldImgPath);
                }

                boolean isSuccess = editionService.updateEdition(edition);

                if (isSuccess) {
                    result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("admin.editions"));

                    if (!oldImgPath.equals(edition.getImagePath())) {
                        DeleteImageFromExternalDirectory.delete(oldImgPath);
                    }
                }
            }
        } catch (UnknownEditionException | MultipartFormException | NullPointerException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.ADMIN);
    }

    private Edition modifyEdition(Edition edition, SessionRequestContent content) throws MultipartFormException {

        Map<String, String> fieldMap = MultipartExtractor.extractMultipart(content);
        String titleUa = fieldMap.get("title_ua");
        String titleEn = fieldMap.get("title_en");
        String textUa = fieldMap.get("text_ua");
        String textEn = fieldMap.get("text_en");
        String price = fieldMap.get("price");
        String genre = fieldMap.get("genre");
        String imgPath = MultipartExtractor.getAbsoluteImagePath(fieldMap.get("file-name"));

        boolean valid = InputValidator.validateNewEdition(titleEn, titleUa, textEn, textUa, price, genre);

        if (valid) {
            edition.setTitleEn(titleEn);
            edition.setTitleUa(titleUa);
            edition.setTextEn(textEn);
            edition.setTextUa(textUa);
            edition.setPrice(Integer.parseInt(price));
            edition.setGenreId(Integer.parseInt(genre));

            if (imgPath != null && !imgPath.equals("")) {
                edition.setImagePath(imgPath);
            }
        } else {
            return null;
        }

        return edition;
    }
}
