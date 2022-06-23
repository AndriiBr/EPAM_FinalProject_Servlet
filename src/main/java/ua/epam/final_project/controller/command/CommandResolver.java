package ua.epam.final_project.controller.command;

import ua.epam.final_project.controller.command.implementation.*;
import ua.epam.final_project.controller.command.security.SecurityChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Resolve requested command from HttpServletRequest
 */
public class CommandResolver {

    private static CommandResolver instance = null;
    private final Map<String, ICommand> commandList = new HashMap<>();

    /**
     * Hidden private constructor
     */
    private CommandResolver(){
        commandList.put("shop/list", new OpenShopListPageCommand());
        commandList.put("shop/list_post", new SearchShopListCommand());
        commandList.put("auth/login", new OpenLoginPageCommand());
        commandList.put("auth/login_post", new SignInCommand());
        commandList.put("auth/logout", new SignOutCommand());
        commandList.put("login/success", new OpenLoginSuccessPageCommand());
        commandList.put("login/fail", new OpenLoginFailPageCommand());
        commandList.put("error/unknown_error", new OpenErrorUnknownCommand());
        commandList.put("auth/registration", new OpenRegistrationPageCommand());
        commandList.put("auth/registration_post", new SignUpCommand());
        commandList.put("registration/success", new OpenRegistrationSuccessPageCommand());
        commandList.put("registration/fail", new OpenRegistrationFailPageCommand());
        commandList.put("user/wallet", new OpenWalletPageCommand());
        commandList.put("wallet/top_up", new OpenWalletTopUpPageCommand());
        commandList.put("wallet/top_up_post", new TopUpBalanceCommand());
        commandList.put("user/subscriptions", new OpenUserSubscriptionsPageCommand());
        commandList.put("list/buy", new OpenEditionBuyPageCommand());
        commandList.put("list/buy_post", new BuyEditionCommand());
        commandList.put("subscriptions/unsubscribe_post", new UnsubscribeEditionCommand());
        commandList.put("admin/edition", new OpenAdminEditionConsoleCommand());
        commandList.put("edition/new-edition", new OpenAddNewEditionPageCommand());
        commandList.put("edition/new-edition_post", new SaveNewEditionCommand());
        commandList.put("edition/delete_post", new DeleteEditionCommand());
        commandList.put("edition/edit", new OpenEditEditionPageCommand());
        //        commandList.put("edition/edit_post", new EditEditionCommand());
    }

    /**
     * Get requested command from the list of commands
     * @param req - HttpServletRequest
     * @return Command entity
     */
    public ICommand initGetCommand(HttpServletRequest req) {
        String reqCommand = extractGetCommand(req);

        return handleCommand(req, reqCommand);
    }

    public ICommand initPostCommand(HttpServletRequest req) {
        String reqCommand = extractPostCommand(req);

        return handleCommand(req, reqCommand);
    }

    /**
     *
     * @param req - HttpServletRequest
     * @param reqCommand - command code
     * @return Command entity
     */
    private ICommand handleCommand(HttpServletRequest req, String reqCommand) {
        HttpSession session = req.getSession();
        CommandHistory commandHistory = (CommandHistory) session.getAttribute("commandHistory");

        if (commandHistory == null) {
            commandHistory = new CommandHistory();
        }

        //Find required command
        ICommand command = commandList.get(reqCommand);
        if (command == null) {
            return new OpenErrorNotFoundCommand();
        }

        //Security access check
        if (!SecurityChecker.getInstance().checkSecurity(req, command)) {
            return new OpenErrorUnknownCommand();
        }

        commandHistory.addNewCommand(reqCommand);
        session.setAttribute("commandHistory", commandHistory);

        return command;
    }

    /**
     * Extract GET command from requestUrl
     * @param req - HttpServletRequest
     * @return String command name
     */
    private String extractGetCommand (HttpServletRequest req) {
        String[] path = req.getRequestURL().toString().split("/");
        return path[path.length - 2] + "/" + path[path.length - 1];
    }

    /**
     * Extract POST command from requestUrl
     * @param req - HttpServletRequest
     * @return String command name
     */
    private String extractPostCommand (HttpServletRequest req) {
        String[] path = req.getRequestURL().toString().split("/");
        return path[path.length - 2] + "/" + path[path.length - 1] + "_post";
    }

    /**
     * Get instance of CommandResolver
     * @return instance
     */
    public static CommandResolver getInstance() {
        if (instance == null) {
            instance = new CommandResolver();
        }

        return instance;
    }
}
