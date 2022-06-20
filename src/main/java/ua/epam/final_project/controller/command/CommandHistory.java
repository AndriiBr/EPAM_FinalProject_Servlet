package ua.epam.final_project.controller.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Store all invoked commands for unique session
 */
public class CommandHistory implements Serializable {

    private final List<String> commandHistoryList;

    public CommandHistory() {
        commandHistoryList = new ArrayList<>();
    }

    /**
     * Add new command
     * @param command - Command name
     */
    public void addNewCommand(String command) {
        commandHistoryList.add(command);
    }

    /**
     * Return last invoked command
     * @return Last command
     */
    public String getLastCommand() {
        if (commandHistoryList.isEmpty()) {
            return null;
        }
        return commandHistoryList.get(commandHistoryList.size() -1);
    }
}
