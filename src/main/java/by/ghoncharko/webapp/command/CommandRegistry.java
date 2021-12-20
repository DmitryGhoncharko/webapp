package by.ghoncharko.webapp.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandRegistry {
    private final Command command;
    private final String commandName;
    private final List<Role> allowedRoles;
    private static final List<CommandRegistry> COMMAND_REGISTRY_LIST = Arrays.asList();
    private CommandRegistry(Command command, String commandName, Role... allowedRoles) {
        this.command = command;
        this.commandName = commandName;
        this.allowedRoles = allowedRoles != null && allowedRoles.length > 0 ? Arrays.asList(allowedRoles) : Collections.emptyList();
    }

    public Command getCommand() {
        return command;
    }

    public static List<Role> getAllowedRoles(String commandName) {
        for (CommandRegistry commandRegistry : COMMAND_REGISTRY_LIST) {
            if (commandRegistry.commandName.equalsIgnoreCase(commandName)) {
                return commandRegistry.allowedRoles;
            }
        }
        return Collections.emptyList();
    }


    static Command of(String name) {
        for (CommandRegistry constant : COMMAND_REGISTRY_LIST) {
            if (constant.commandName.equalsIgnoreCase(name)) {
                return constant.command;
            }
        }
        return MAIN_PAGE.command;
    }
}
