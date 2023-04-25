package cz.gryga.avitech.command;

public class InvalidCommandArgumentException extends RuntimeException {

    public InvalidCommandArgumentException(CommandType commandType, String argument) {
        super(String.format("Invalid argument of %s command: %s", commandType.name(), argument));
    }
}
