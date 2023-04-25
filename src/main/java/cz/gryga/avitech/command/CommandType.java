package cz.gryga.avitech.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * All supportedCommands
 */
public enum CommandType implements CommandMatcher {
    ADD(1, "^\\s*Add\\s+(\\(.*\\))\\s*$"),
    PRINT_ALL(0, "^\\s*PrintAll\\s*$"),
    DELETE_ALL(0, "^\\s*DeleteAll\\s*$");

    private final int numberOfArguments;

    private final Pattern pattern;

    CommandType(int numberOfArguments, String commandRegex) {
        this.pattern = Pattern.compile(commandRegex);
        this.numberOfArguments = numberOfArguments;
    }

    @Override
    public boolean matches(String command) {
        return pattern.matcher(command).matches();
    }

    @Override
    public String getArgument(int index, String command) {
        if (index >= numberOfArguments) {
            throw new IndexOutOfBoundsException(String.format("Command %s does not have %d argument.", name(), index));
        }

        Matcher matcher = pattern.matcher(command);
        if (!matcher.matches()) {
            throw new UnknownCommandException(String.format("Command %s does not match %s command syntax.", command, name()));
        }
        return matcher.group(index + 1);
    }
}
