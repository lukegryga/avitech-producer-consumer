package cz.gryga.avitech.command;

/**
 * Matches command and extracts arguments.
 */
public interface CommandMatcher {

    /**
     * @return true if string matches command syntax
     */
    boolean matches(String command);

    /**
     * Get argument at given index.
     * @param index first argument has index 0
     * @param command complete command with arguments
     * @return command argument
     * @throws IllegalArgumentException if command does not match command syntax
     * @throws IndexOutOfBoundsException if command does not have argument at given index
     */
    String getArgument(int index, String command);
}
