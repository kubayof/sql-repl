package example;

import example.command.Commands;
import example.command.GenerateLogCommand;
import example.command.InitDbCommand;
import example.command.SqlCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandsTests {
    @Test
    public void initCommandTest() {
        commandsTest("init", InitDbCommand.class);
    }

    @Test
    public void logCommandTest() {
        commandsTest("log customers", GenerateLogCommand.class);
    }

    @Test
    public void sqlCommandTest() {
        commandsTest("SELECT * FROM customers", SqlCommand.class);
    }

    private void commandsTest(String command, Class<?> commandClass) {
        Assertions.assertTrue(commandClass.isAssignableFrom(Commands.get(command).getClass()));
    }
}
