package cz.gryga.avitech.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTypeTest {

    private static final String[] unknownCommands = new String[] {
            "", "Unknown", "Ad", "Print", "All", "printall, Ad d, Ad>d"};

    private static final String[] invalidArguments = new String[] {
            "Add (1, A1, \"Aladin\")",
            "Add",
            "Add 1, \"A1\", \"Aladin\""};


    @Test
    void matchAdd() {
        assertThat(CommandType.ADD.matches("Add  (1, \"A1\", \"Aladin\" )")).isTrue();
        assertThat(CommandType.ADD.matches(" Add (2,  \"B2\", \"Borek\" ) ")).isTrue();
        assertThat(CommandType.ADD.matches("Add   ( 3, \"C3\", \"Cecil\")")).isTrue();
    }

    @Test
    void getArgumentAdd() {
        assertThat(CommandType.ADD.getArgument(0, "Add (1, \"A1\", \"Aladin\")"))
                .isEqualTo("(1, \"A1\", \"Aladin\")");
    }

    @Test
    void matchPrintAll() {
        assertThat(CommandType.PRINT_ALL.matches("PrintAll")).isTrue();
    }

    @Test
    void matchDeleteAll() {
        assertThat(CommandType.DELETE_ALL.matches("DeleteAll")).isTrue();
    }

    @ParameterizedTest
    @EnumSource(CommandType.class)
    void matchesUnknownCommand(CommandMatcher commandMatcher) {

        for (String unknownCommand : unknownCommands) {
            assertThat(commandMatcher.matches(unknownCommand)).isFalse();
        }
    }

    @ParameterizedTest
    @EnumSource(
            value = CommandType.class,
            names = {"ADD"},
            mode = EnumSource.Mode.INCLUDE)
    void getArgumentUnknownCommand(CommandMatcher commandMatcher) {
        for (String unknownCommand : unknownCommands) {
            assertThatThrownBy(() -> commandMatcher.getArgument(0, unknownCommand))
                    .isInstanceOf(UnknownCommandException.class);
        }
    }

    @ParameterizedTest
    @EnumSource(CommandType.class)
    void getArgumentOutOfBound(CommandMatcher commandMatcher) {
        for (String invalidArgument : invalidArguments) {
            assertThatThrownBy(() -> commandMatcher.getArgument(3, invalidArgument))
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }
    }
}