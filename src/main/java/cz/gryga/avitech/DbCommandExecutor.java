package cz.gryga.avitech;

import cz.gryga.avitech.command.CommandExecutor;
import cz.gryga.avitech.command.CommandType;
import cz.gryga.avitech.command.InvalidCommandArgumentException;
import cz.gryga.avitech.command.UnknownCommandException;
import cz.gryga.avitech.db.entity.User;
import cz.gryga.avitech.db.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Can parse and execute commands.
 */
public class DbCommandExecutor implements CommandExecutor {

    private final UserRepository userRepository;

    private final Pattern userArgumentPattern = Pattern.compile("\\(\\s*(\\d+)\\s*,\\s*\"(.*)\"\\s*,\\s*\"(.*)\"\\s*\\)");

    /**
     * @param userRepository user repository
     */
    public DbCommandExecutor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Parse and Execute command.
     * @param command command to execute
     * @throws UnknownCommandException if command does not match any supportedCommands
     * @throws InvalidCommandArgumentException if arguments of the known command are invalid
     */
    @Override
    public void execute(String command) {
        if (CommandType.ADD.matches(command)) {
            add(CommandType.ADD.getArgument(0, command));
        } else if (CommandType.PRINT_ALL.matches(command)) {
            printAll();
        } else if (CommandType.DELETE_ALL.matches(command)) {
            deleteAll();
        } else {
            throw new UnknownCommandException(command);
        }
    }

    private void add(String user) {
        Matcher userArgumentMatcher = userArgumentPattern.matcher(user);
        if (!userArgumentMatcher.matches()) {
            throw new InvalidCommandArgumentException(CommandType.ADD, user);
        }

        long id = Long.parseLong(userArgumentMatcher.group(1));
        String guid = userArgumentMatcher.group(2);
        String name = userArgumentMatcher.group(3);

        userRepository.persist(new User(id, guid, name));
    }

    private void printAll() {
        userRepository.listAll().forEach(System.out::println);
    }

    private void deleteAll() {
        userRepository.deleteAll();
    }
}
