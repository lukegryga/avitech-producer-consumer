package cz.gryga.avitech;

import cz.gryga.avitech.command.CommandExecutor;
import cz.gryga.avitech.command.InvalidCommandArgumentException;
import cz.gryga.avitech.command.UnknownCommandException;
import cz.gryga.avitech.db.entity.User;
import cz.gryga.avitech.db.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DbCommandExecutorTest {

    private UserRepository userRepository;

    private CommandExecutor commandExecutor;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;


    private final User user1 = new User(1L, "A1", "Aladin");
    private final String sUser1 = "Add (1, \"A1\", \"Aladin\")";

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        commandExecutor = new DbCommandExecutor(userRepository);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void executeUnknownCommand() {
        assertThatThrownBy(() -> commandExecutor.execute("Unknown"))
                .isInstanceOf(UnknownCommandException.class);

        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void executeCommandInvalidArgument() {
        assertThatThrownBy(() -> commandExecutor.execute("Add (1, A1,)"))
                .isInstanceOf(InvalidCommandArgumentException.class);

        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void executeAddCommand() {
        commandExecutor.execute(sUser1);

        Mockito.verify(userRepository).persist(Mockito.eq(user1));

        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void executePrintAllCommand() {
        Mockito.when(userRepository.listAll()).thenReturn(List.of(user1));

        commandExecutor.execute("PrintAll");

        assertThat(outContent.toString()).contains(user1.getGuid(), user1.getName(), String.valueOf(user1.getId()));

        Mockito.verify(userRepository).listAll();
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteAll() {
        commandExecutor.execute("DeleteAll");

        Mockito.verify(userRepository).deleteAll();
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void commandSequence() {
        Mockito.when(userRepository.listAll()).thenReturn(List.of(user1));

        commandExecutor.execute(sUser1);
        commandExecutor.execute("PrintAll");

        assertThatThrownBy(() -> commandExecutor.execute("Unknown"))
                .isInstanceOf(UnknownCommandException.class);

        commandExecutor.execute("DeleteAll");
        commandExecutor.execute("PrintAll");

        assertThat(outContent.toString()).contains(user1.getGuid(), user1.getName(), String.valueOf(user1.getId()));

        Mockito.verify(userRepository).persist(Mockito.eq(user1));
        Mockito.verify(userRepository, Mockito.times(2)).listAll();
        Mockito.verify(userRepository).deleteAll();
        Mockito.verifyNoMoreInteractions(userRepository);
    }


}