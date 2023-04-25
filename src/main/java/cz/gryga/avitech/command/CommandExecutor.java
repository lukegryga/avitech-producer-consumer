package cz.gryga.avitech.command;

/**
 * Can parse and execute commands.
 */
public interface CommandExecutor {

    /**
     * Parse and Execute command.
     * @param command command to execute
     * @throws UnknownCommandException if command does not match any supportedCommands
     * @throws InvalidCommandArgumentException if arguments of the known command are invalid
     */
    void execute(String command);
}
